package mobi.moop.features.reserva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.DisponibilidadeBem;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class DisponibilidadesViewHolder extends RecyclerView.ViewHolder {

    private final DisponibilidadesAdapter adapter;
    private final String diaSemana;

    private Context context;

    @BindView(R.id.textInicioDisp)
    TextView textInicioDisponibilidade;

    @BindView(R.id.textNomeBem)
    TextView textNomeBem;

    @BindView(R.id.textFimDisp)
    TextView textFimDisponibilidade;

    @BindView(R.id.textDiaSemana)
    TextView textDiaSemana;

    @OnClick(R.id.textReservar)
    public void reservar(View view) {
        adapter.reservar(getAdapterPosition());
    }

    public DisponibilidadesViewHolder(View itemView, DisponibilidadesAdapter adapter, String diaSemana) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        this.adapter = adapter;
        this.diaSemana = diaSemana;
    }

    public void bindView(DisponibilidadeBem disponibilidadeBem) {
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
        textDiaSemana.setText(diaSemana);
        textInicioDisponibilidade.setText("De " + horaFormat.format(disponibilidadeBem.getHorarioInicial()));
        textFimDisponibilidade.setText("Até " + horaFormat.format(disponibilidadeBem.getHorarioFinal()));
        textNomeBem.setText(disponibilidadeBem.getBemUsoComum().getNome());
    }
}
