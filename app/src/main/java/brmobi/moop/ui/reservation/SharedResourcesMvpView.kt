package brmobi.moop.ui.reservation

import brmobi.moop.ui.base.MvpView
import java.io.Serializable

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourcesMvpView : MvpView {

    fun openAvailableActivity(sharedResourceExtraKey: String, serializable: Serializable)

    fun selectMyReservationsFragment()

    fun showAutorizationView()

    fun showRecyclerView()

    fun notifyDataSetChanged()

    fun stopRefreshing()

}