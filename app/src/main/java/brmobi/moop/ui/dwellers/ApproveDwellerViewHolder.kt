package brmobi.moop.ui.dwellers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.item_aprovar_morador.view.*

/**
 * Created by murilo aires on 16/10/2017.
 */

class ApproveDwellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textNome = itemView.text_nome
    val textUnidade = itemView.text_unidade
    val imgAvatar = itemView.imgAvatar as ImageView
    val btnApprove = itemView.aprove
    val btnDesapprove = itemView.desaprove

}
