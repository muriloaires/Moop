package brmobi.moop.ui.reservation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 12/08/2017.
 */

class AvailabilityAdapter(val mPresenter: AvailabilityMvpPresenter<AvailabilityMvpView>) : RecyclerView.Adapter<AvailabilityViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailabilityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.disponibilidade_item, parent, false)
        return AvailabilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvailabilityViewHolder, position: Int) {
        holder.bindView(mPresenter.getAvailabilities()[0], mPresenter.getDayOfWeek())
        holder.textReserve.setOnClickListener {
            mPresenter.onTextReserveClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getAvailabilities().size
    }
}
