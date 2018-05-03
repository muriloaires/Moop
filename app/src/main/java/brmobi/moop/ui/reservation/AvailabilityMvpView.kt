package brmobi.moop.ui.reservation

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 24/02/2018.
 */
interface AvailabilityMvpView : MvpView {
    fun finishWithOkResult()
    fun notifyDataSetChanged()
}