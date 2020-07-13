package com.love.kotllinpro.base

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.*
import com.afollestad.materialdialogs.color.CircleView


import com.love.kotllinpro.R
import com.love.kotllinpro.constant.Constant
import com.love.kotllinpro.event.NetworkChangeEvent
import com.love.kotllinpro.receiver.NetworkChangeReceiver
import com.love.kotllinpro.utils.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {
    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null;
    protected var mThemeColor: Int = SettingUtil.getColor()

    protected lateinit var mTipView: View
    protected lateinit var mWindowManager: WindowManager
    protected lateinit var mLayoutParams: WindowManager.LayoutParams
    /**
     * 绑定布局文件，abstract修饰的方法必须覆写
     */
    abstract fun attachLayoutRes(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    abstract fun initView()
    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 是否使用EventBus，open代表可以被继承和覆写，当然也可以不覆写
     */
    open fun useEventBus(): Boolean = true

    /**
     * 是否需要显示TipView
     */
    open fun enableNetworkTip(): Boolean = true

    /**
     *
     */
    open fun doReConnected(){
        start()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        if(useEventBus()){
            EventBus.getDefault().register(this)
        }
        initData()
        initTipView()
        initView()
        start()

    }
    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    protected fun initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        toolbar?.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initTipView(){
        mTipView=layoutInflater.inflate(R.layout.layout_network_tip,null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }
    override fun onResume() {
        // 动态注册网络变化广播
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()
        initColor()

        // 在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查

        // 1.第一次进入界面会导致 start() 方法走两次
        // 2.后台切换到前台时，会调用 start() 方法执行相应的操作
        // 此处不应该调用，删掉，修改 #13
        // checkNetwork(hasNetwork)

    }

    open fun initColor() {
//        mThemeColor = if (!SettingUtil.getIsNightMode()) {
//            SettingUtil.getColor()
//        } else {
//            resources.getColor(R.color.colorPrimary)
//        }
       // StatusBarUtil.setColor(this, mThemeColor, 0)
//        if (this.supportActionBar != null) {
//            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = CircleView.shiftColorDown(mThemeColor)
//            // 最近任务栏上色
//            val tDesc = ActivityManager.TaskDescription(
//                    getString(R.string.app_name),
//                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
//                    mThemeColor)
//            setTaskDescription(tDesc)


//            if (SettingUtil.getNavBar()) {
//                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
//            } else {
//                window.navigationBarColor = Color.BLACK
//            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)

    }
    override fun finish() {
        super.finish()
        if (mTipView != null && mTipView.parent != null) {
            mWindowManager.removeView(mTipView)
        }
    }
    private fun checkNetwork(isConnected: Boolean) {
        if (enableNetworkTip()) {
            if (isConnected) {
                doReConnected()
                if (mTipView != null && mTipView.parent != null) {
                    mWindowManager.removeView(mTipView)
                }
            } else {
                if (mTipView.parent == null) {
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
        }
    }

}
