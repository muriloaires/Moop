package brmobi.moop.ui.tickets

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.R
import brmobi.moop.data.network.model.Chamado
import brmobi.moop.data.network.model.StatusChamado
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_chamado.view.*
import java.text.SimpleDateFormat

/**
 * Created by murilo aires on 02/10/2017.
 */

class ChamadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textTicketTitle = itemView.text_titulo_chamado
    val textTicketDescription = itemView.text_descricao
    val statusBackground = itemView.status_background
    val textStatus = itemView.textoStatus
    val textOpenningTime = itemView.text_data_abertura
    val imgTicket = itemView.img_foto_chamado

    private val context: Context = itemView.context


    fun bindView(chamado: Chamado) {
        populateFields(chamado)
    }

    private fun populateFields(chamado: Chamado) {
        when (chamado!!.status) {
            StatusChamado.ABERTO -> {
                textStatus.text = context.getString(R.string.aberto)
                statusBackground!!.background.setColorFilter(context.resources.getColor(android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY)
            }
            StatusChamado.EM_ANDAMENTO -> {
                textStatus.text = context.getString(R.string.em_andamento)
                statusBackground!!.background.setColorFilter(context.resources.getColor(R.color.amarelo_analise), PorterDuff.Mode.MULTIPLY)
            }
            StatusChamado.RESOLVIDO -> {
                textStatus.text = context.getString(R.string.resolvido)
                statusBackground.background.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY)
            }
            else -> {
                textStatus.text = context.getString(R.string.cancelado)
                statusBackground.background.setColorFilter(context.resources.getColor(android.R.color.darker_gray), PorterDuff.Mode.MULTIPLY)
            }
        }
        textTicketTitle.text = chamado!!.titulo
        textTicketDescription.text = chamado!!.texto

        textOpenningTime.text = "Aberto em " + SimpleDateFormat("dd/MM/yyyy").format(chamado.updatedAt) + " às " + SimpleDateFormat("HH:mm").format(chamado!!.updatedAt)
        if (!chamado.imagem.isNullOrEmpty()) {
            Picasso.with(context).load(chamado.imagem).into(imgTicket)
        } else {
            imgTicket.setColorFilter(ContextCompat.getColor(context, R.color.cinza))
        }

    }
}
