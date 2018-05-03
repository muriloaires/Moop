package brmobi.moop.ui.messages

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 25/02/2018.
 */
interface MessagesMvpView : MvpView {
    fun setTitle(userDestinyName: String)
    fun notifyDataSetChanged()
    fun showMessageOptions(position: Int)
    fun clearEditText()
}