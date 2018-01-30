package brmobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.model.entities.DisponibilidadeBem;
import brmobi.moop.R;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class DisponibilidadesAdapter extends RecyclerView.Adapter<DisponibilidadesViewHolder> {
    private final DisponibilidadesFragment fragment;
    private List<DisponibilidadeBem> disponibilidades;
    private String diaSemana;

    public DisponibilidadesAdapter(List<DisponibilidadeBem> disponibilidades, DisponibilidadesFragment fragment) {
        this.disponibilidades = disponibilidades;
        this.fragment = fragment;
    }

    @Override
    public DisponibilidadesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disponibilidade_item, parent, false);
        return new DisponibilidadesViewHolder(view, this,diaSemana);
    }

    @Override
    public void onBindViewHolder(DisponibilidadesViewHolder holder, int position) {
        holder.bindView(disponibilidades.get(0));
    }

    @Override
    public int getItemCount() {
        return disponibilidades.size();
    }

    public void reservar(int adapterPosition) {
        fragment.reservarDisponibilidade(adapterPosition);
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}
