package brmobi.moop.ui.tickets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 02/10/2017.
 */

class TicketsAdapter(private val mvpPresenter: TicketListMvpPresenter<TicketListMvpView>) : RecyclerView.Adapter<ChamadoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChamadoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chamado, parent, false)
        return ChamadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChamadoViewHolder, position: Int) {
        holder.bindView(mvpPresenter.getTickets()[position])
    }

    override fun getItemCount(): Int {
        return mvpPresenter.getTickets().size
    }
}
