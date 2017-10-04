package mobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.BemComum;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BensComunsAdapter extends RecyclerView.Adapter {
    private static final int BENS_COMUNS_HEADER = 0;
    private static final int BEM_COMUM = 1;
    private List<BemComum> bensComuns;

    public BensComunsAdapter(List<BemComum> bensComuns) {
        this.bensComuns = bensComuns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BEM_COMUM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bem_comum, parent, false);
            return new BemComumViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bem_comum_header, parent, false);
            return new BemComumHeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BemComumViewHolder) {
            ((BemComumViewHolder) holder).bindView(bensComuns.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return bensComuns.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (bensComuns.get(position) == null) {
            return BENS_COMUNS_HEADER;
        } else {
            return BEM_COMUM;
        }
    }
}
