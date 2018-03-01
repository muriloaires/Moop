package brmobi.moop.ui.reservation

import android.support.v7.widget.RecyclerView
import android.view.View
import brmobi.moop.data.network.model.ReservaBemComum
import kotlinx.android.synthetic.main.item_reserva.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by murilo aires on 14/08/2017.
 */

class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val textResourceName = view.textNomeBem
    val textReservationDay = view.textDiaReserva
    val textDurationTime = view.textTempoDuracao
    val textScheduleDate = view.textDataAgendamento
    val textValue = view.textValor
    val textCancelReservation = view.textCancelarReserva

    fun bindView(reserva: ReservaBemComum) {
        val horaFormat = SimpleDateFormat("HH:mm")
        val diaMesAnoFormat = SimpleDateFormat("dd/MM/yyyy")
        textResourceName.text = reserva.disponibilidadeUsoBemComum.bemUsoComum.nome
        val calendar = Calendar.getInstance()
        calendar.time = reserva.dataUso
        val diaSemana = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        val mes = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        textReservationDay.text = "De " + diaSemana.toUpperCase() + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + mes + " às " + horaFormat.format(reserva.disponibilidadeUsoBemComum.horarioInicial)
        textDurationTime.text = "Até " + horaFormat.format(reserva.disponibilidadeUsoBemComum.horarioFinal)
        textScheduleDate.text = "Reservado dia " + diaMesAnoFormat.format(reserva.createdAt) + " às " + horaFormat.format(reserva.createdAt)
        textValue.text = "Valor: " + reserva.bemUsoComum.valorCurrency
    }

}
