package brmobi.moop.ui.dwellers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.data.network.model.PerfilHabitacional
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_morador.view.*

/**
 * Created by murilo aires on 30/09/2017.
 */

class DwellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgAvatar = itemView.imgAvatar
    private val textNome = itemView.text_nome
    private val textUnidadeBloco = itemView.text_unidade_bloco
    private val context: Context = itemView.context
    val btnSendMessage = itemView.btn_enviar_mensagem


    fun bindView(morador: PerfilHabitacional) {
        if (!morador.perfil.avatar.isNullOrEmpty()) {
            Picasso.with(context).load(morador.perfil.avatar).placeholder(R.drawable.placeholder_avatar).error(R.drawable.placeholder_avatar).into(imgAvatar)
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar)
        }
        textNome!!.text = morador.perfil.nome
        textUnidadeBloco!!.text = "Bloco " + morador.unidadeHabitacional.bloco.nome + " Unidade " + morador.unidadeHabitacional.numero
    }
}
