package brmobi.moop.ui.reservation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.data.network.model.BemComum
import brmobi.moop.utils.AppConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_bem_comum.view.*

/**
 * Created by murilo aires on 12/08/2017.
 */

class SharedResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textSharedResourceName = itemView.bemComumNome
    private val textPrice = itemView.text_preco
    private val imgSharedResource = itemView.img_bem_comum
    val rootView = itemView.rootView
    private val context: Context = itemView.context

    fun bindView(bemComum: BemComum) {
        textSharedResourceName.text = bemComum.nome
        textPrice!!.text = bemComum.valorCurrency
        if (!bemComum.avatar.isNullOrEmpty()) {
            Picasso.with(context).load(AppConstants.BASE_URL + bemComum.avatar).into(imgSharedResource)
        }
    }
}
