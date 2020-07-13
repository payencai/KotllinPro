package com.love.kotllinpro.base

interface IPresenter<in V : IView> {
    fun attachView(mView: V)
    fun detachView()
}