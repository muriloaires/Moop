package mobi.moop.features.condominio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import mobi.moop.R;
import mobi.moop.model.entities.Unidade;

/**
 * Created by Logics on 19/08/2017.
 */

public class UnidadesAdapter extends RecyclerView.Adapter<UnidadeViewHolder> {

    private List<Unidade> unidades;

    public UnidadesAdapter(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    @Override
    public UnidadeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unidade, parent, false);
        return new UnidadeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnidadeViewHolder holder, int position) {
        holder.bindView(unidades.get(position));
    }

    @Override
    public int getItemCount() {
        return unidades.size();
    }
}
