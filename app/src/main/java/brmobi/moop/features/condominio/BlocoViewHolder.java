package brmobi.moop.features.condominio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import brmobi.moop.model.entities.Bloco;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class BlocoViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    @BindView(R.id.textBloco)
    TextView textBloco;

    private Bloco bloco;
    private Long condominioId;

    @OnClick(R.id.rootView)
    public void selectBloco(View view) {
        ((AddCondominioActivity) context).showUnidadesFragment(bloco);
    }

    public BlocoViewHolder(View itemView, Long condominioId) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        this.condominioId = condominioId;
    }

    public void bindView(Bloco bloco) {
        this.bloco = bloco;
        textBloco.setText(bloco.getNome());
    }
}
