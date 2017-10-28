package mobi.moop.features.moradores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.PerfilHabitacional;

/**
 * Created by murilo aires on 16/10/2017.
 */

public class AprovarMoradoresAdapter extends RecyclerView.Adapter<AprovarMoradorViewHolder> {
    private List<PerfilHabitacional> moradores;

    public AprovarMoradoresAdapter(List<PerfilHabitacional> moradores) {
        this.moradores = moradores;
    }

    @Override
    public AprovarMoradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aprovar_morador, parent, false);
        return new AprovarMoradorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AprovarMoradorViewHolder holder, int position) {
        holder.bindView(moradores.get(position));
    }

    @Override
    public int getItemCount() {
        return moradores.size();
    }
}
