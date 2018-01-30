package brmobi.moop.features.reserva;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.model.entities.ReservaBemComum;
import brmobi.moop.R;

/**
 * Created by Logics on 09/10/2017.
 */

public class ReservasAdapter extends RecyclerView.Adapter<ReservaViewHolder> {
    private final MinhasReservasFragment fragment;
    private List<ReservaBemComum> reservas;

    public ReservasAdapter(List<ReservaBemComum> reservas, MinhasReservasFragment fragment) {
        this.reservas = reservas;
        this.fragment = fragment;
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder holder, int position) {
        holder.bindView(reservas.get(position));
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public void cancelarReserva(int adapterPosition) {
        fragment.cancelarReserva(adapterPosition);
    }
}
