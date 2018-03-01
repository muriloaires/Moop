package brmobi.moop.ui.notifications

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 22/02/2018.
 */
interface NotificationMvpView : MvpView {

    fun showCommentsActivity(extraKey: String, extraValue: Long)

    fun showApproveDwellersActivity()
    fun stopRefreshing()
    fun notifyDataSetChanged()

}