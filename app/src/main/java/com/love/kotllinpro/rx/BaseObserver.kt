package com.love.kotllinpro.rx


import com.love.kotllinpro.app.App
import com.love.kotllinpro.base.IView
import com.love.kotllinpro.http.exception.ErrorStatus
import com.love.kotllinpro.http.exception.ExceptionHandle
import com.love.kotllinpro.mvp.model.bean.BaseBean
import com.love.kotllinpro.utils.NetWorkUtil
import io.reactivex.observers.ResourceObserver

/**
 * Created by chenxz on 2018/6/11.
 */
abstract class BaseObserver<T : BaseBean> : ResourceObserver<T> {

    private var mView: IView? = null
    private var mErrorMsg = ""
    private var bShowLoading = true

    constructor(view: IView) {
        this.mView = view
    }

    constructor(view: IView, bShowLoading: Boolean) {
        this.mView = view
        this.bShowLoading = bShowLoading
    }

    /**
     * 成功的回调
     */
    protected abstract fun onSuccess(t: T)

    /**
     * 错误的回调
     */
    protected fun onError(t: T) {}

    override fun onStart() {
        super.onStart()
        if (bShowLoading) mView?.showLoading()
        if (!NetWorkUtil.isNetworkConnected(App.instance)) {
            mView?.showDefaultMsg("当前网络不可用，请检查网络设置")
            onComplete()
        }
    }

    override fun onNext(t: T) {
        mView?.hideLoading()
        when {
            t.errorCode == ErrorStatus.SUCCESS -> onSuccess(t)
            t.errorCode == ErrorStatus.TOKEN_INVALID -> {
                // TODO Token 过期，重新登录
            }
            else -> {
                onError(t)
                if (t.errorMsg.isNotEmpty())
                    mView?.showDefaultMsg(t.errorMsg)
            }
        }
    }

    override fun onError(e: Throwable) {
        mView?.hideLoading()
        if (mView == null) {
            throw RuntimeException("mView can not be null")
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(e)
        }
        mView?.showDefaultMsg(mErrorMsg)
    }

    override fun onComplete() {
        mView?.hideLoading()
    }
}

