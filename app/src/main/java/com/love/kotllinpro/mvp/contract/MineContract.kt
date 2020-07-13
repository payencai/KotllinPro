package com.love.kotllinpro.mvp.contract

import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface MineContract {
    interface View :CommonContract.View{
        fun setUserInfo()
        fun setBalanceInfo()
        fun setOrderInfo()
        fun setFansInfo()
    }
    interface Presenter:CommonContract.Presenter<View>{
        fun requestUserInfo()
        fun requestBalanceInfo()
        fun requestOrderInfo()
        fun requestFansInfo()

    }
    interface Model:CommonContract.Model{
        fun requestUserInfo():Observable<HttpResult<Any>>
        fun requestBalanceInfo():Observable<HttpResult<Any>>
        fun requestOrderInfo():Observable<HttpResult<Any>>
        fun requestFansInfo():Observable<HttpResult<Any>>
    }
}