package mobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.entities.ReservaBemComum;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BensComunsAdapter extends RecyclerView.Adapter {
    private static final int BENS_COMUNS_HEADER = 0;
    private static final int BEM_COMUM = 1;
    private static final int RESERVA = 2;
    private static final int RESERVA_HEADER = 3;
    private List<Object> bensComuns;

    public BensComunsAdapter(List<Object> bensComuns) {
        this.bensComuns = bensComuns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BEM_COMUM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bem_comum, parent, false);
            return new BemComumViewHolder(view);
        } else if (viewType == BENS_COMUNS_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bem_comum_header, parent, false);
            return new BemComumHeaderViewHolder(view);
        } else if (viewType == RESERVA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
            return new ReservaViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva_header, parent, false);
            return new ReservaHeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BemComumViewHolder) {
            ((BemComumViewHolder) holder).bindView((BemComum) bensComuns.get(position));
        } else if (holder instanceof ReservaViewHolder) {
            ((ReservaViewHolder) holder).bindView((ReservaBemComum) bensComuns.get(position));
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
        } else if (bensComuns.get(position) instanceof BemComum) {
            return BEM_COMUM;
        } else if (bensComuns.get(position) instanceof ReservaBemComum) {
            return RESERVA;
        } else {
            return RESERVA_HEADER;
        }
    }
}
