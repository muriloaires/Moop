package brmobi.moop.ui.condominium.add

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 18/08/2017.
 */

class BlocsAdapter(private val mPresenter: BlocMvpPresenter<BlocMvpView>) : RecyclerView.Adapter<BlocViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlocViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bloco, parent, false)
        return BlocViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlocViewHolder, position: Int) {
        holder.bindView(mPresenter.getBlocs()[position])
        holder.rootView.setOnClickListener {
            mPresenter.onBlocSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getBlocs().size
    }
}
