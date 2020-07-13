package com.love.kotllinpro.base

interface IView {
    fun showLoading()
    fun hideLoading()
    fun showDefaultMsg(msg:String)
    fun showMsg(msg:String)
    fun showError(errorMsg: String)
}