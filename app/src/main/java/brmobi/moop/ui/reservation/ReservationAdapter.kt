package brmobi.moop.ui.reservation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by Logics on 09/10/2017.
 */

class ReservationAdapter(val mPresenter: MyReservationsMvpPresenter<MyReservationsMvpView>) : RecyclerView.Adapter<ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bindView(mPresenter.getReservations()[position])
        holder.textCancelReservation.setOnClickListener { mPresenter.onCancelReservationClick(position) }
    }

    override fun getItemCount(): Int {
        return mPresenter.getReservations().size
    }

}
