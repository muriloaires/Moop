package mobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.DisponibilidadeBem;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class DisponibilidadesAdapter extends RecyclerView.Adapter<DisponibilidadesViewHolder> {
    private List<DisponibilidadeBem> disponibilidades;

    public DisponibilidadesAdapter(List<DisponibilidadeBem> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    @Override
    public DisponibilidadesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disponibilidade_item, parent, false);
        return new DisponibilidadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DisponibilidadesViewHolder holder, int position) {
        holder.bindView(disponibilidades.get(0));
    }

    @Override
    public int getItemCount() {
        return disponibilidades.size();
    }
}
