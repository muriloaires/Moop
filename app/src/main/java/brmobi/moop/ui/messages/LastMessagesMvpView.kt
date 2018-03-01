package brmobi.moop.ui.messages

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 21/02/2018.
 */
interface LastMessagesMvpView : MvpView {

    fun notifyDataSetChanged()
    fun showDwellersActivity()
    fun openMessagesActivity(extraUserDestinationIdKey: String, extraUserDestinationIdValue: Long, extraUserDestinationNameKey: String, nameValue: String)
    fun stopRefresh()
}