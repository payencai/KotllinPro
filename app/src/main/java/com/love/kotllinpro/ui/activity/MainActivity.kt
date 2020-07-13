package com.love.kotllinpro.ui.activity

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.internal.BottomNavigationItemView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cxz.wanandroid.utils.DialogUtil
import com.love.kotllinpro.R
import com.love.kotllinpro.app.App
import com.love.kotllinpro.base.BaseMvpActivity
import com.love.kotllinpro.constant.Constant
import com.love.kotllinpro.event.ColorEvent
import com.love.kotllinpro.event.LoginEvent
import com.love.kotllinpro.event.RefreshHomeEvent
import com.love.kotllinpro.ext.showToast
import com.love.kotllinpro.mvp.contract.MainContract
import com.love.kotllinpro.mvp.model.bean.UserInfoBody
import com.love.kotllinpro.mvp.presenter.MainPresenter
import com.love.kotllinpro.ui.fragment.*
import com.love.kotllinpro.utils.Preference
import com.love.kotllinpro.utils.StatusBarUtil

import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor
import org.jetbrains.anko.uiThread


class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    private var mIndex = TAG_FRAGMENT_HOME
    private var mExitTime: Long = 0
    private var mHomeFragment: HomeFragment? = null
    private var mPreferFragment: PreferFragment? = null
    private var mShopCarFragment: ShopCarFragment? = null
    private var mMineFragment: MineFragment? = null
    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    companion object {
        private const val BOTTOM_INDEX: String = "bottom_index"
        private const val TAG_FRAGMENT_HOME = 0x01
        private const val TAG_FRAGMENT_YOU = 0x02
        private const val TAG_FRAGMENT_CAR = 0x03
        private const val TAG_FRAGMENT_MINE = 0x04
    }

    override fun useEventBus(): Boolean = true
    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this@MainActivity, resources.getString(R.string.logout_ing))
    }

    override fun attachLayoutRes(): Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState?.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(this, resources.getColor(R.color.Grey_f5),0)
    }


    override fun initData() {
        Beta.checkUpgrade(false, false)

    }

    override fun start() {
        mPresenter?.getUserInfo()

    }

    override fun initColor() {
        super.initColor()
        refreshColor(ColorEvent(true))
    }

    override fun initView() {
        super.initView()
        initListener()
        showFragment(mIndex)
    }

    private fun initListener() {
        ll_tab_home.setOnClickListener(onClickListener)
        ll_tab_prefered.setOnClickListener(onClickListener)
        ll_tab_car.setOnClickListener(onClickListener)
        ll_tab_mine.setOnClickListener(onClickListener)
    }

    /**
     * 点击事件
     */
    private val onClickListener = View.OnClickListener { view ->

        when (view.id) {
            R.id.ll_tab_home -> {
                StatusBarUtil.setColor(this, resources.getColor(R.color.Grey_f5),0)
                StatusBarUtil.setLightStatusBar(this,true)
                setGreyIconAndText()
                tv_tab_home.textColor=resources.getColor(R.color.Red_login)
                iv_tab_home.imageResource=R.mipmap.ic_red_home
                showFragment(TAG_FRAGMENT_HOME)
            }
            R.id.ll_tab_prefered -> {
                StatusBarUtil.setColor(this, resources.getColor(R.color.Grey_f5),0)
                StatusBarUtil.setLightStatusBar(this,true)
                setGreyIconAndText()
                tv_tab_you.textColor=resources.getColor(R.color.Red_login)
                iv_tab_you.imageResource=R.mipmap.ic_red_prefered
                showFragment(TAG_FRAGMENT_YOU)
            }
            R.id.ll_tab_car -> {
                StatusBarUtil.setColor(this, resources.getColor(R.color.white),0)
                StatusBarUtil.setLightStatusBar(this,true)
                setGreyIconAndText()
                tv_tab_car.textColor=resources.getColor(R.color.Red_login)
                iv_tab_car.imageResource=R.mipmap.ic_red_car
                showFragment(TAG_FRAGMENT_CAR)
            }
            R.id.ll_tab_mine -> {
                StatusBarUtil.setColor(this, resources.getColor(R.color.Black_26),0)
                StatusBarUtil.setLightStatusBar(this,false)
                setGreyIconAndText()
                tv_tab_mine.textColor=resources.getColor(R.color.Red_login)
                iv_tab_mine.imageResource=R.mipmap.ic_red_mine
                showFragment(TAG_FRAGMENT_MINE)
            }
        }

    }


    private fun setGreyIconAndText(){
        iv_tab_home.imageResource=R.mipmap.ic_grey_home
        iv_tab_you.imageResource=R.mipmap.ic_grey_prefered
        iv_tab_car.imageResource=R.mipmap.ic_grey_car
        iv_tab_mine.imageResource=R.mipmap.ic_grey_mine

        tv_tab_home.textColor=resources.getColor(R.color.Grey_4f)
        tv_tab_you.textColor=resources.getColor(R.color.Grey_4f)
        tv_tab_car.textColor=resources.getColor(R.color.Grey_4f)
        tv_tab_mine.textColor=resources.getColor(R.color.Grey_4f)
    }

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            TAG_FRAGMENT_HOME -> {
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            TAG_FRAGMENT_YOU -> {
                if (mPreferFragment == null) {
                    mPreferFragment = PreferFragment.getInstance()
                    transaction.add(R.id.container, mPreferFragment!!, "you")
                } else {
                    transaction.show(mPreferFragment!!)
                }
            }
            TAG_FRAGMENT_CAR -> {
                if (mShopCarFragment == null) {
                    mShopCarFragment = ShopCarFragment.getInstance()
                    transaction.add(R.id.container, mShopCarFragment!!, "car")
                }else{
                    transaction.show(mShopCarFragment!!)
                }
            }
            TAG_FRAGMENT_MINE -> {
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.getInstance()
                    transaction.add(R.id.container, mMineFragment!!, "mine")
                }else{
                    transaction.show(mMineFragment!!)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mPreferFragment?.let { transaction.hide(it) }
        mShopCarFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    override fun showLogoutSuccess(success: Boolean) {
        if (success) {
            doAsync {
                // CookieManager().clearAllCookies()
                Preference.clearPreference()
                uiThread {
                    mDialog.dismiss()
                    showToast(resources.getString(R.string.logout_success))
                    isLogin = false
                    EventBus.getDefault().post(LoginEvent(false))
                }
            }
        }
    }


    override fun showUserInfo(bean: UserInfoBody) {
        App.userInfo = bean
    }

    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (mHomeFragment != null) {
                fragmentTransaction.remove(mHomeFragment!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
    }

    /**
     * 去登陆页面
     */
    private fun goLogin() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        if (event.isLogin) {
            mHomeFragment?.lazyLoad()
            mPresenter?.getUserInfo()
        } else {

            mHomeFragment?.lazyLoad()
            // 重置用户信息

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshHomeEvent(event: RefreshHomeEvent) {
        if (event.isRefresh) {
            mHomeFragment?.lazyLoad()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOTTOM_INDEX, mIndex)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomeFragment = null
        mPreferFragment = null
        mShopCarFragment = null
        mMineFragment = null
    }
}


