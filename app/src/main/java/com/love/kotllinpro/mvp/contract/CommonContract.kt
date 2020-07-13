package com.love.kotllinpro.mvp.contract

import com.love.kotllinpro.base.IModel
import com.love.kotllinpro.base.IPresenter
import com.love.kotllinpro.base.IView
import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface CommonContract {
    interface View:IView{
        fun showCollectSuccess(success:Boolean)
        fun showCancelCollectSuccess(success: Boolean)
    }
    interface Presenter<in V:View>:IPresenter<V>{
        fun addCollectArticle(id: Int)
        fun cancelCollectArticle(id: Int)
    }
    interface Model:IModel{
        fun addCollectArticle(id:Int): Observable<HttpResult<Any>>
        fun cancelCollectArticle(id:Int): Observable<HttpResult<Any>>
    }
}