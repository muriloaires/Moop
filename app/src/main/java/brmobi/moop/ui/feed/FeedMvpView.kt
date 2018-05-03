package brmobi.moop.ui.feed

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 21/02/2018.
 */
interface FeedMvpView : MvpView {

    fun openNewPostActivity()
    fun showRecycler()
    fun showLockedCondominium()
    fun resetScrollListenerState()
    fun stopRefreshing()
    fun showNoPublications()
    fun notifyDataSetChanged()
    fun removeScrollListener()
    fun showFeedItemOptions()
    fun showCommentsActivity(feedIdExtraKey: String, feedIdExtraValue: Long)
    fun showImagePostActivity(imgPostExtraKey: String, url: String)

}