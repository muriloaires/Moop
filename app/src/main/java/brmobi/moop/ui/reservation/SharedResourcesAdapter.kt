package brmobi.moop.ui.reservation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 12/08/2017.
 */

class SharedResourcesAdapter(val mPresenter: SharedResourcesMvpPresenter<SharedResourcesMvpView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == BEM_COMUM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bem_comum, parent, false)
            SharedResourceViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bem_comum_header, parent, false)
            SharedResourceHeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bemComum = mPresenter.getSharedResources()[position]
        if (bemComum != null) {
            (holder as SharedResourceViewHolder).bindView(bemComum)
            holder.rootView.setOnClickListener { mPresenter.onSharedResourceClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getSharedResources().size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mPresenter.getSharedResources()[position] == null) {
            BENS_COMUNS_HEADER
        } else {
            BEM_COMUM
        }
    }

    companion object {
        const val BENS_COMUNS_HEADER = 0
        const val BEM_COMUM = 1
    }

}
