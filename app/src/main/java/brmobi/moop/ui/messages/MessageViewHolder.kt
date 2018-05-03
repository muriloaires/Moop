package brmobi.moop.ui.messages

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.utils.DateUtils
import brmobi.moop.data.network.model.Mensagem
import brmobi.moop.utils.AppConstants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mensagem.view.*
import java.util.*

/**
 * Created by murilo aires on 30/09/2017.
 */

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgAvatar = itemView.imgAvatar
    private val textNome = itemView.textNome
    private val textMessage = itemView.textMensagem
    private val textMesssageTime = itemView.textHoraMensagem
    private val context: Context = itemView.context

    val rootView = itemView.rootView

    fun bindView(mensagem: Mensagem) {
        textMessage.setText(mensagem.mensagem)
        textNome.setText(mensagem.dePerfil.nome)
        if (mensagem.dePerfil.avatar != "") {
            Picasso.with(context).load(mensagem.dePerfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, object : Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                    Picasso.with(context).load(AppConstants.BASE_URL + mensagem.dePerfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar)
                }
            })
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar)
        }
        textMesssageTime.text = DateUtils.getDifference(Date(), mensagem.createdAt)
    }
}
