package brmobi.moop.ui.comments

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 24/02/2018.
 */
interface CommentsMvpView : MvpView {
    fun notifyDataSetChanged()
    fun showDeleteOptions(position: Int)
    fun clearEditTextComment()
}