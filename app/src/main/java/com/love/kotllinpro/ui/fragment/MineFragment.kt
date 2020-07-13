package com.love.kotllinpro.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.love.kotllinpro.R
import com.love.kotllinpro.base.BaseMvpFragment
import com.love.kotllinpro.mvp.contract.HomeContract
import com.love.kotllinpro.mvp.contract.MineContract
import com.love.kotllinpro.mvp.presenter.MinePresenter

/**
 * A simple [Fragment] subclass.
 */
class MineFragment : BaseMvpFragment<MineContract.View, MineContract.Presenter>(), MineContract.View {
    companion object {
        fun getInstance(): MineFragment = MineFragment()
    }

    override fun createPresenter(): MineContract.Presenter =MinePresenter()

    override fun attachLayoutRes(): Int =R.layout.fragment_mine

    override fun lazyLoad() {

    }

    override fun setUserInfo() {

    }

    override fun setBalanceInfo() {

    }

    override fun setOrderInfo() {

    }

    override fun setFansInfo() {

    }

    override fun showCollectSuccess(success: Boolean) {

    }

    override fun showCancelCollectSuccess(success: Boolean) {

    }


}
