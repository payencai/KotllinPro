package com.love.kotllinpro.mvp.presenter

import com.love.kotllinpro.ext.ss
import com.love.kotllinpro.mvp.contract.HomeContract
import com.love.kotllinpro.mvp.model.HomeModel
import com.love.kotllinpro.mvp.model.bean.Article
import com.love.kotllinpro.mvp.model.bean.ArticleResponseBody
import com.love.kotllinpro.mvp.model.bean.Banner
import com.love.kotllinpro.mvp.model.bean.HttpResult
import com.love.kotllinpro.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter : CommonPresenter<HomeContract.Model, HomeContract.View>(),
    HomeContract.Presenter {


    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun requestBanner() {
        mModel?.requestBanner()?.ss(mModel, mView, false) {
            mView?.setBanner(it.data)
        }
    }

    override fun requestHomeData() {
        requestBanner()
        val observable = if (SettingUtil.getIsShowTopArticle()) {
            mModel?.requestArticles(0)
        } else {
            //这是RXJAVA里面的组合多个请求的操作符 new Function(param1,param2,Return)
            Observable.zip(
                mModel?.requestTopArticles(),
                mModel?.requestArticles(0),
                BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>, HttpResult<ArticleResponseBody>> { t1, t2 ->
                    t1.data.forEach {
                        it.top = "1"
                    }
                    t2.data.datas.addAll(0, t1.data)
                    t2  //返回HttpResult<ArticleResponseBody>类型的参数
                })
        }
        observable?.ss(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.requestArticles(num)?.ss(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }


}