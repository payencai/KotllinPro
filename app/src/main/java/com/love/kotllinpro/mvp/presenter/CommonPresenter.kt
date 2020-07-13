package com.love.kotllinpro.mvp.presenter

import com.love.kotllinpro.base.BasePresenter
import com.love.kotllinpro.ext.ss
import com.love.kotllinpro.mvp.contract.CommonContract

open class CommonPresenter<M:CommonContract.Model,V:CommonContract.View>: BasePresenter<M, V>(),CommonContract.Presenter<V> {
    override fun addCollectArticle(id: Int) {
       mModel?.addCollectArticle(id)?.ss(mModel,mView){
           mView?.showCollectSuccess(true)
       }
    }

    override fun cancelCollectArticle(id: Int) {
        mModel?.cancelCollectArticle(id)?.ss(mModel, mView) {
            mView?.showCancelCollectSuccess(true)
        }
    }

}