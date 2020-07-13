package com.love.kotllinpro.ui.fragment

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxz.multiplestatusview.MultipleStatusView
import com.love.kotllinpro.R
import com.love.kotllinpro.adapter.HomeAdapter
import com.love.kotllinpro.app.App
import com.love.kotllinpro.base.BaseMvpFragment
import com.love.kotllinpro.ext.showToast
import com.love.kotllinpro.mvp.contract.HomeContract
import com.love.kotllinpro.mvp.model.bean.Article
import com.love.kotllinpro.mvp.model.bean.ArticleResponseBody
import com.love.kotllinpro.mvp.model.bean.Banner
import com.love.kotllinpro.mvp.presenter.HomePresenter
import com.love.kotllinpro.utils.ImageLoader
import com.love.kotllinpro.utils.NetWorkUtil
import com.love.kotllinpro.widget.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import kotlinx.android.synthetic.main.item_home_banner.view.*

class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }
    private var isRefresh = true
    private val articleList = mutableListOf<Article>()
    private lateinit var bannerList: ArrayList<Banner>
    private var bannerView: View? = null
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.load(activity, feedImageUrl, imageView)
        }
    }
    private val recycleViewItemDeclaration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }
    private val homeAdapter by lazy {
        HomeAdapter(activity, articleList)
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (articleList.size != 0) {
            val data = articleList[position]
            //ContentActivity.start(activity, data.id, data.title, data.link)
        }
    }
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (bannerList.size != 0) {

            }
        }
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeAdapter.setEnableLoadMore(false)
        mPresenter?.requestHomeData()
    }
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = homeAdapter.data.size / 20
        mPresenter?.requestArticles(page)
    }
    private val bannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
        if (bannerList.size > 0) {
            val data = bannerList[position]
            //ContentActivity.start(activity, data.id, data.title, data.url)
        }
    }
    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recycleViewItemDeclaration?.let { addItemDecoration(it) }
        }

        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        bannerView?.banner?.run {
            setDelegate(bannerDelegate)
        }

        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@HomeFragment.onItemClickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            // setEmptyView(R.layout.fragment_empty_layout)
            addHeaderView(bannerView)
        }

    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestHomeData()
    }

    override fun showLoading() {
        // swipeRefreshLayout.isRefreshing = isRefresh
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        homeAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
    }

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun setBanner(banners: List<Banner>) {
        bannerList = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
            .subscribe { list ->
                bannerFeedList.add(list.imagePath)
                bannerTitleList.add(list.title)
            }
        bannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }

    override fun setArticles(articles: ArticleResponseBody) {
        articles.datas.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < articles.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
        if (homeAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.cancel_collect_success))
        }
    }


}