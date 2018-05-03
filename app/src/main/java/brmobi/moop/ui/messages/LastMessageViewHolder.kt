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
import kotlinx.android.synthetic.main.item_ultima_mensagem.view.*
import java.util.*

/**
 * Created by murilo aires on 02/10/2017.
 */

class LastMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgAvatar = itemView.imgAvatar
    private val textName = itemView.textNome
    private val textMessage = itemView.textMensagem
    private val textMessageTime = itemView.textHoraMensagem
    private val context: Context = itemView.context
    val rootView = itemView.rootView
    private var prefix = ""

    fun bindView(mensagem: Mensagem) {
        prefix = if (mensagem.fromLoggedUser) "Você: " else ""
        textMessage.text = prefix + mensagem.mensagem
        textName.text = mensagem.otherUser.nome
        if (!mensagem.otherUser.avatar.isNullOrEmpty()) {
            Picasso.with(context).load(mensagem.otherUser.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar!!, object : Callback {
                override fun onSuccess() {
                }

                override fun onError() {
                    Picasso.with(context).load(AppConstants.BASE_URL + mensagem.otherUser.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar)
                }
            })
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar)
        }
        textMessageTime!!.text = DateUtils.getDifferenceSmall(Date(), mensagem.createdAt)
    }
}
