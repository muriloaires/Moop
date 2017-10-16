package mobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.ReservaBemComum;

/**
 * Created by murilo aires on 14/08/2017.
 */

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    private final ReservasAdapter adapter;

    @BindView(R.id.textNomeBem)
    TextView textNomeBem;

    @BindView(R.id.textDiaReserva)
    TextView textDiaReserva;

    @BindView(R.id.textTempoDuracao)
    TextView textTempoDuracao;

    @BindView(R.id.textDataAgendamento)
    TextView textDataAgendamento;

    @BindView(R.id.textValor)
    TextView textValor;

    @OnClick(R.id.textCancelarReserva)
    public void textCancelarReservaAction(View view) {
        adapter.cancelarReserva(getAdapterPosition());
    }

    public ReservaViewHolder(View view, ReservasAdapter adapter) {
        super(view);
        ButterKnife.bind(this, view);
        this.adapter = adapter;
    }


    public void bindView(ReservaBemComum reserva) {
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat diaMesAnoFormat = new SimpleDateFormat("dd/MM/yyyy");
        textNomeBem.setText(reserva.getDisponibilidadeUsoBemComum().getBemUsoComum().getNome());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reserva.getDataUso());
        String diaSemana = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String mes = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        textDiaReserva.setText("De " + capitalize(diaSemana) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + mes + " às " + horaFormat.format(reserva.getDisponibilidadeUsoBemComum().getHorarioInicial()));
        textTempoDuracao.setText("Até " + horaFormat.format(reserva.getDisponibilidadeUsoBemComum().getHorarioFinal()));
        textDataAgendamento.setText("Reservado dia " + diaMesAnoFormat.format(reserva.getCreatedAt()) + " às " + horaFormat.format(reserva.getCreatedAt()));
        textValor.setText("Valor: " + reserva.getBemUsoComum().getValorCurrency());
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
