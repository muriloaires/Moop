package brmobi.moop.ui.dwellers

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 22/02/2018.
 */
interface ApproveDwellersMvpView : MvpView {
    fun notifyDataSetChanged()
    fun showDialogApprove(position: Int)
    fun showDialogDesaprove(position: Int)
}