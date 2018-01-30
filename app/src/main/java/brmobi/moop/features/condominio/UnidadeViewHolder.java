package brmobi.moop.features.condominio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import brmobi.moop.model.entities.Unidade;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * Created by Logics on 19/08/2017.
 */

public class UnidadeViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    @BindView(R.id.textUnidade)
    TextView textUnidade;

    private Unidade unidade;

    @OnClick(R.id.rootView)
    public void selectUnidade(View view) {
    }


    public UnidadeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Unidade unidade) {
        this.unidade = unidade;
        textUnidade.setText(unidade.getNumero().toString());
    }
}
