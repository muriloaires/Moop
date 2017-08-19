package mobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.ReservaBemComum;

/**
 * Created by murilo aires on 14/08/2017.
 */

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textNomeBem)
    TextView textNomeBem;

    @BindView(R.id.textDiaReserva)
    TextView textDiaReserva;

    @BindView(R.id.textTempoDuracao)
    TextView textTempoDuracao;

    public ReservaViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    public void bindView(ReservaBemComum reserva) {
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
        textNomeBem.setText(reserva.getDisponibilidadeUsoBemComum().getBemUsoComum().getNome());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reserva.getDataUso());
        String diaSemana = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String mes = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        textDiaReserva.setText(capitalize(diaSemana) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + mes);
        textTempoDuracao.setText("De " + horaFormat.format(reserva.getDisponibilidadeUsoBemComum().getHorarioInicial()) + " até " + horaFormat.format(reserva.getDisponibilidadeUsoBemComum().getHorarioFinal()));
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
