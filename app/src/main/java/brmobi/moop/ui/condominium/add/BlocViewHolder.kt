package brmobi.moop.ui.condominium.add

import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.data.network.model.Bloco
import kotlinx.android.synthetic.main.item_bloco.view.*

/**
 * Created by murilo aires on 18/08/2017.
 */

class BlocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val rootView = itemView.rootView
    val textBloc = itemView.textBloco

    fun bindView(bloco: Bloco) {
        textBloc.text = bloco.nome
    }
}
