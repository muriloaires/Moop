package brmobi.moop.ui.reservation

import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.data.network.model.DisponibilidadeBem
import kotlinx.android.synthetic.main.disponibilidade_item.view.*
import java.text.SimpleDateFormat

/**
 * Created by murilo aires on 12/08/2017.
 */

class AvailabilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private val textAvailabilityInit = itemView.textInicioDisp
    private val textSharedResourceName = itemView.textNomeBem
    private val textAvailabilityEnd = itemView.textFimDisp
    private val textDayOfWeek = itemView.textDiaSemana
    val textReserve = itemView.textReservar


    fun bindView(disponibilidadeBem: DisponibilidadeBem, diaSemana: String) {
        val horaFormat = SimpleDateFormat("HH:mm")
        textDayOfWeek.text = diaSemana
        textAvailabilityInit.text = "De " + horaFormat.format(disponibilidadeBem.horarioInicial)
        textAvailabilityEnd.text = "Até " + horaFormat.format(disponibilidadeBem.horarioFinal)
        textSharedResourceName.text = disponibilidadeBem.bemUsoComum.nome
    }
}
