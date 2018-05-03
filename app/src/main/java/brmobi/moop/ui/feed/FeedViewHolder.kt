package brmobi.moop.ui.feed

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.MoopApplication
import brmobi.moop.R
import brmobi.moop.utils.DateUtils
import brmobi.moop.data.network.model.FeedItem
import brmobi.moop.utils.AppConstants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_feed.view.*
import java.util.*

/**
 * Created by murilo aires on 27/07/2017.
 */

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val context: Context = itemView.context

    private val imgAvatar = itemView.imgAvatar
    private val textName = itemView.textNome
    private val text = itemView.textTexto
    val imgPost = itemView.imgPost
    val textCountComments = itemView.textNumeroComments
    private val textCountLikes = itemView.textNumeroCurtidas
    private val textCommentTime = itemView.textHoraComentario
    val imgLike = itemView.imgCurtir
    val imgUnlike = itemView.imgDescurtir
    val imgOptions = itemView.options

    private lateinit var feedItem: FeedItem


    fun bindView(feedItem: FeedItem) {
        this.feedItem = feedItem
        textName.text = feedItem.perfil.nome
        textCommentTime.text = DateUtils.getDifference(Date(), feedItem.data)
        textCountComments.text = " " + feedItem.comentarios.toString()
        textCountLikes.text = " " + feedItem.curtidas.toString()

        imgOptions!!.visibility = if (feedItem.fromLoggedUser) View.VISIBLE else View.GONE
        if (feedItem.perfil.avatar != "") {
            Picasso.with(context).load(feedItem.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar!!, object : Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                    Picasso.with(context).load(AppConstants.BASE_URL + this@FeedViewHolder.feedItem.perfil.avatar).placeholder(R.drawable.placeholder_avatar).into(imgAvatar)
                }
            })
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar)
        }

        if (feedItem.texto == null || feedItem.texto == "") {
            text.visibility = View.GONE
        } else {
            text.visibility = View.VISIBLE
            text.text = feedItem.texto
        }

        if (feedItem.imagem == null || feedItem.imagem == "") {
            imgPost!!.visibility = View.GONE
        } else {
            imgPost!!.visibility = View.VISIBLE
            val constante = MoopApplication.screenWidth.toFloat() / feedItem.getwImage()
            val imgViewWidth = (constante * feedItem.getwImage()).toInt()
            val imgViewHeight = (constante * feedItem.gethImage()).toInt()
            val params = imgPost.layoutParams
            params.width = imgViewWidth
            params.height = imgViewHeight
            imgPost.layoutParams = params
            Picasso.with(context).load(AppConstants.BASE_URL + feedItem.imagem)
                    .noFade()
                    .placeholder(R.drawable.feedplaceholder)
                    .into(imgPost)
        }

        if (feedItem.isCurtidaPeloUsuario) {
            imgUnlike.visibility = View.VISIBLE
            imgLike.visibility = View.GONE
        } else {
            imgUnlike.visibility = View.GONE
            imgLike.visibility = View.VISIBLE
        }
    }

}
