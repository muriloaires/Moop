package brmobi.moop.features.chamado;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.model.entities.Chamado;
import brmobi.moop.R;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class ChamadosAdapter extends RecyclerView.Adapter<ChamadoViewHolder> {


    private List<Chamado> chamados;

    public ChamadosAdapter(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    @Override
    public ChamadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamado, parent, false);
        return new ChamadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChamadoViewHolder holder, int position) {
        holder.bindView(chamados.get(position));
    }

    @Override
    public int getItemCount() {
        return chamados.size();
    }
}
