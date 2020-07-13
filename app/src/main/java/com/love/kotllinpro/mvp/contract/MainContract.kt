package com.love.kotllinpro.mvp.contract

import com.love.kotllinpro.base.IModel
import com.love.kotllinpro.base.IPresenter
import com.love.kotllinpro.base.IView
import com.love.kotllinpro.mvp.model.bean.HttpResult
import com.love.kotllinpro.mvp.model.bean.UserInfoBody
import io.reactivex.Observable

interface MainContract {
    interface View : IView {
        fun showLogoutSuccess(success: Boolean)
        fun showUserInfo(bean: UserInfoBody)
    }

    interface Presenter : IPresenter<View> {
        fun logout()
        fun getUserInfo()
    }

    interface Model : IModel {
        fun logout(): Observable<HttpResult<Any>>
        fun getUserInfo(): Observable<HttpResult<UserInfoBody>>
    }
}