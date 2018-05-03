package brmobi.moop.ui.dwellers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 30/09/2017.
 */

class DwellersAdapter(private val mPresenter: DwellersMvpPresenter<DwellersMvpView>) : RecyclerView.Adapter<DwellerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DwellerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_morador, parent, false)
        return DwellerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DwellerViewHolder, position: Int) {
        holder.bindView(mPresenter.getDwellers()[position])
        holder.btnSendMessage.setOnClickListener {
            mPresenter.openMessageActivity(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getDwellers().size
    }
}
