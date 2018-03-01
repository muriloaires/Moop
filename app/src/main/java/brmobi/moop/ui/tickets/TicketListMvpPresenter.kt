package brmobi.moop.ui.tickets

import brmobi.moop.data.network.model.Chamado
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 21/02/2018.
 */
interface TicketListMvpPresenter<V : TicketListMvpView> : MvpPresenter<V> {

    fun onBtnCriarChamadoClick()
    fun getTickets(): List<Chamado>
    fun onResumeCalled()

}