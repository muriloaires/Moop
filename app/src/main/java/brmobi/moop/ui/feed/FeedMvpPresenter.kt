package brmobi.moop.ui.feed

import brmobi.moop.data.network.model.FeedItem
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 21/02/2018.
 */
interface FeedMvpPresenter<V : FeedMvpView> : MvpPresenter<V> {
    fun onViewReady()
    fun getItems(): List<FeedItem?>
    fun onBtnLikeClick(adapterPosition: Int)
    fun onBtnUnlikeClick(adapterPosition: Int)
    fun onOptionsClick(adapterPosition: Int)
    fun onFabCreatePostClick()
    fun onRefreshUpdated()
    fun onLoadMore()
    fun onDeleteItemFeedClick()
    fun onNewPostActivitySuccessResult()
    fun btnCommentsClick(position: Int)
    fun onImgPostClick(position: Int)
}