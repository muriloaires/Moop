package brmobi.moop.features.moradores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.model.entities.PerfilHabitacional;
import brmobi.moop.R;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class MoradoresAdapter extends RecyclerView.Adapter<MoradorViewHolder> {
    private List<PerfilHabitacional> moradores;

    public MoradoresAdapter(List<PerfilHabitacional> moradores) {
        this.moradores = moradores;
    }

    @Override
    public MoradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_morador, parent, false);
        return new MoradorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoradorViewHolder holder, int position) {
        holder.bindView(moradores.get(position));
    }

    @Override
    public int getItemCount() {
        return moradores.size();
    }
}
