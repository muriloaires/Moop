package brmobi.moop.ui.condominium.add

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 17/08/2017.
 */

class CondominiosAdapter(val mPresenter: CondominiumMvpPresenter<CondominiumMvpView>) : RecyclerView.Adapter<CondominioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CondominioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_condominio, parent, false)
        return CondominioViewHolder(view)
    }

    override fun onBindViewHolder(holder: CondominioViewHolder, position: Int) {
        holder.bindView(mPresenter.getCondominiuns()[position])
        holder.rootView.setOnClickListener {
            mPresenter.onCondominiumSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getCondominiuns().size
    }
}
