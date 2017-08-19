package mobi.moop.features.condominio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Bloco;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class BlocoViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    @BindView(R.id.textBloco)
    TextView textBloco;

    private Bloco bloco;

    @OnClick(R.id.rootView)
    public void selectBloco(View view) {
        ((AddCondominioActivity) context).showUnidadesFragment(bloco.getId());
    }

    public BlocoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Bloco bloco) {
        this.bloco = bloco;
        textBloco.setText(bloco.getNome());
    }
}
