package mobi.moop.features.chamado;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import mobi.moop.model.entities.Chamado;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class ChamadoViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private Chamado chamado;

    public ChamadoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Chamado chamado) {
        this.chamado = chamado;
        populateFields();
    }

    private void populateFields() {

    }
}
