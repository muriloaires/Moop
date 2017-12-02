package mobi.moop.features.moradores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.PerfilHabitacional;
import mobi.moop.model.entities.Usuario;

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
