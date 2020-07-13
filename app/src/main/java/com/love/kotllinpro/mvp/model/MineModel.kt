package com.love.kotllinpro.mvp.model

import com.love.kotllinpro.mvp.contract.MineContract
import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class MineModel:MineContract.Model {
    override fun requestUserInfo(): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestBalanceInfo(): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestOrderInfo(): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestFansInfo(): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDisposable(disposable: Disposable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}