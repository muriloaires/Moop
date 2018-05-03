package brmobi.moop.ui.reservation

import brmobi.moop.data.network.model.ReservaBemComum
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 22/02/2018.
 */
interface MyReservationsMvpPresenter<V : MyReservationsMvpView> : MvpPresenter<V> {

    fun onViewReady()

    fun getReservations(): List<ReservaBemComum>

    fun onCancelReservationClick(position: Int)

    fun onConfirmCancelation(position: Int)

    fun onRefreshListener()
    fun onResume()

}