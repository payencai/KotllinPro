package com.love.kotllinpro.mvp.presenter

import com.love.kotllinpro.mvp.contract.HomeContract
import com.love.kotllinpro.mvp.contract.MineContract
import com.love.kotllinpro.mvp.model.HomeModel
import com.love.kotllinpro.mvp.model.MineModel

class MinePresenter: CommonPresenter<MineContract.Model, MineContract.View>(),MineContract.Presenter {
    override fun createModel(): MineContract.Model? = MineModel()
    override fun requestUserInfo() {

    }

    override fun requestBalanceInfo() {

    }

    override fun requestOrderInfo() {

    }

    override fun requestFansInfo() {

    }
}