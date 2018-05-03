package brmobi.moop.ui.comments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.data.network.model.Comentario
import brmobi.moop.utils.AppConstants
import brmobi.moop.utils.DateUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comentario.view.*
import java.util.*

/**
 * Created by murilo aires on 11/08/2017.
 */

class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgAvatar = itemView.imgAvatar
    private val textName = itemView.textNome
    private val textComment = itemView.textComentario
    private val textCommentTime = itemView.textHoraComentario

    val rootView = itemView.rootView

    private val context: Context = itemView.context


    fun bindView(comentario: Comentario) {
        textComment.text = comentario.texto
        textName.text = comentario.perfil.nome
        if (comentario.perfil.avatar != "") {
            Picasso.with(context).load(comentario.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar!!, object : Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                    Picasso.with(context).load(AppConstants.BASE_URL + comentario.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar)
                }
            })
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar)
        }
        textCommentTime!!.text = DateUtils.getDifference(Date(), comentario.createdAt)
    }
}
