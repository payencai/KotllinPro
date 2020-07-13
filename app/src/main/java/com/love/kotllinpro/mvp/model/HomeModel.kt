package com.love.kotllinpro.mvp.model

import com.love.kotllinpro.http.RetrofitHelper
import com.love.kotllinpro.mvp.contract.HomeContract
import com.love.kotllinpro.mvp.model.bean.Article
import com.love.kotllinpro.mvp.model.bean.ArticleResponseBody
import com.love.kotllinpro.mvp.model.bean.Banner
import com.love.kotllinpro.mvp.model.bean.HttpResult
import io.reactivex.Observable

class HomeModel : CommonModel(),HomeContract.Model{
    override fun requestBanner(): Observable<HttpResult<List<Banner>>> {
       return RetrofitHelper.service.getBanners()
    }

    override fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>> {
       return RetrofitHelper.service.getTopArticles()
    }

    override fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>> {
       return RetrofitHelper.service.getArticles(num)
    }
}