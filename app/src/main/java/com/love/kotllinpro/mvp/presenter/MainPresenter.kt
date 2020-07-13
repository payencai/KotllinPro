package com.love.kotllinpro.mvp.presenter

import com.love.kotllinpro.base.BasePresenter
import com.love.kotllinpro.ext.ss
import com.love.kotllinpro.ext.sss
import com.love.kotllinpro.mvp.contract.MainContract
import com.love.kotllinpro.mvp.model.MainModel

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter{
    override fun createModel(): MainContract.Model? = MainModel()

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView) {
            mView?.showLogoutSuccess(success = true)
        }
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.sss(mView, false, {
            mView?.showUserInfo(it.data)
        }, {})
    }
}