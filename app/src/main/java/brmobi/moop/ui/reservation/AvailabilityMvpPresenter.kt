package brmobi.moop.ui.reservation

import android.os.Bundle
import brmobi.moop.data.network.model.DisponibilidadeBem
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 24/02/2018.
 */
interface AvailabilityMvpPresenter<V : AvailabilityMvpView> : MvpPresenter<V> {
    fun getDayOfWeek(): String
    fun getAvailabilities(): List<DisponibilidadeBem>
    fun onTextReserveClick(position: Int)
    fun handleArguments(arguments: Bundle)
    fun onViewReady()
}