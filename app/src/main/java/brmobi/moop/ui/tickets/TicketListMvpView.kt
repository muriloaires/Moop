package brmobi.moop.ui.tickets

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 21/02/2018.
 */
interface TicketListMvpView : MvpView {

    fun showNewTicketFragment()

    fun notifyDataSetChanged()

    fun showNoTicketsView()

    fun showTicketsView()

}