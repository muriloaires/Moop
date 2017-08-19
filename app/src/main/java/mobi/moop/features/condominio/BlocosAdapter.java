package mobi.moop.features.condominio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Bloco;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class BlocosAdapter extends RecyclerView.Adapter<BlocoViewHolder> {

    private List<Bloco> blocos;
    private Long condominioId;

    public BlocosAdapter(List<Bloco> blocos, Long condominioId) {
        this.blocos = blocos;
        this.condominioId = condominioId;
    }

    @Override
    public BlocoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bloco, parent, false);
        return new BlocoViewHolder(view, condominioId);
    }

    @Override
    public void onBindViewHolder(BlocoViewHolder holder, int position) {
        holder.bindView(blocos.get(position));
    }

    @Override
    public int getItemCount() {
        return blocos.size();
    }
}
