package brmobi.moop.ui.condominium.add

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.data.db.model.Condominio
import kotlinx.android.synthetic.main.item_condominio.view.*

/**
 * Created by murilo aires on 17/08/2017.
 */

class CondominioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context

    private val textCondominiumName = itemView.textCondominioNome
    val rootView = itemView.selectable_view

    fun bindView(condominio: Condominio) {
        textCondominiumName.text = condominio.nome
        rootView!!.setBackgroundColor(if (condominio.isSelected) context.resources.getColor(R.color.selected_condominio) else Color.WHITE)
    }
}
