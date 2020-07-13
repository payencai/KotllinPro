package com.love.kotllinpro.mvp.model

import com.love.kotllinpro.base.BaseModel
import com.love.kotllinpro.http.RetrofitHelper
import com.love.kotllinpro.mvp.contract.CommonContract
import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

open class CommonModel:CommonContract.Model,BaseModel() {
    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
    }

    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
    }


}