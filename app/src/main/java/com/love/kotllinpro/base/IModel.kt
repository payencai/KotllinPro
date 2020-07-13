package com.love.kotllinpro.base

import io.reactivex.disposables.Disposable

interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()
}