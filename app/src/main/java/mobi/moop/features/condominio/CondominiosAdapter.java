package mobi.moop.features.condominio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Condominio;

/**
 * Created by murilo aires on 17/08/2017.
 */

public class CondominiosAdapter extends RecyclerView.Adapter<CondominioViewHolder> {

    private CondominiosFragment fragment;
    private List<Condominio> condominios;

    public CondominiosAdapter(List<Condominio> condominios, CondominiosFragment fragment) {
        this.condominios = condominios;
        this.fragment = fragment;
    }

    @Override
    public CondominioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condominio, parent, false);
        return new CondominioViewHolder(view, fragment);
    }

    @Override
    public void onBindViewHolder(CondominioViewHolder holder, int position) {
        holder.bindView(condominios.get(position));
    }

    @Override
    public int getItemCount() {
        return condominios.size();
    }
}
