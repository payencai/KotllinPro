package com.love.kotllinpro.mvp.model

import com.love.kotllinpro.base.BaseModel
import com.love.kotllinpro.http.RetrofitHelper
import com.love.kotllinpro.mvp.contract.MainContract
import com.love.kotllinpro.mvp.model.bean.HttpResult
import com.love.kotllinpro.mvp.model.bean.UserInfoBody
import io.reactivex.Observable

class MainModel : BaseModel(), MainContract.Model {

    override fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.logout()
    }

    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
        return RetrofitHelper.service.getUserInfo()
    }

}