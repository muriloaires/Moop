package brmobi.moop.ui.reservation

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 22/02/2018.
 */
interface MyReservationsMvpView : MvpView {

    fun showDialogCancelation(position: Int)

    fun notifyDataSetChanged()
}