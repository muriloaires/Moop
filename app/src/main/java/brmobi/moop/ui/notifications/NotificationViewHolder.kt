package brmobi.moop.ui.notifications

import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.data.network.model.Notificacao
import brmobi.moop.notification.NotificationController
import brmobi.moop.utils.AppConstants
import brmobi.moop.utils.DateUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_notificacao.view.*
import java.util.*

/**
 * Created by murilo aires on 21/12/2017.
 */

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val avatar = itemView.avatar
    val textNome = itemView.textNome
    val textAcao = itemView.textAcao
    val textHora = itemView.textHora
    val rootView = itemView.rootView
    val context = rootView.context


    fun bindView(notificacao: Notificacao) {
        if (notificacao.perfil.avatar != "") {
            Picasso.with(context).load(notificacao.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(avatar!!, object : Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                    Picasso.with(context).load(AppConstants.BASE_URL + notificacao.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(avatar)
                }
            })
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(avatar)
        }
        textNome.text = notificacao.perfil.nome
        textHora!!.text = DateUtils.getDifference(Date(), notificacao.createdAt)
        setAcaoText(notificacao)
    }

    private fun setAcaoText(notificacao: Notificacao) {
        when (notificacao.tipo) {
            NotificationController.TIPO_NOVA_CURTIDA -> textAcao!!.text = "Curtiu sua publicação"
            NotificationController.TIPO_NOVO_COMENTARIO_FEED -> textAcao!!.text = "Comentou em sua publicação: " + notificacao!!.mensagem
            NotificationController.TIPO_SOLICITACAO_MORADOR -> textAcao!!.text = notificacao.mensagem
            NotificationController.TIPO_NOVO_USUARIO_LIBERADO -> textAcao.text = notificacao.mensagem
        }
    }
}
