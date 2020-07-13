package com.love.kotllinpro.mvp.contract

import com.love.kotllinpro.mvp.model.bean.Article
import com.love.kotllinpro.mvp.model.bean.ArticleResponseBody
import com.love.kotllinpro.mvp.model.bean.Banner
import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface HomeContract {
    interface View : CommonContract.View {
        fun scrollToTop()
        fun setBanner(banners: List<Banner>)
        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestBanner()
        fun requestHomeData()
        fun requestArticles(num: Int)
    }
    interface Model:CommonContract.Model{
        fun requestBanner(): Observable<HttpResult<List<Banner>>>

        fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>

        fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>>
    }
}