package brmobi.moop.features.condominio;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import brmobi.moop.model.entities.Condominio;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * Created by murilo aires on 17/08/2017.
 */

public class CondominioViewHolder extends RecyclerView.ViewHolder {

    private CondominiosFragment fragment;
    private Context context;

    @BindView(R.id.textCondominioNome)
    TextView textNomeCondominio;

    @BindView(R.id.selectable_view)
    View selectableView;

    @OnClick(R.id.selectable_view)
    public void select(View view) {
       fragment.select(getAdapterPosition());
    }

    public CondominioViewHolder(View itemView, CondominiosFragment fragment) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        this.fragment = fragment;
    }

    public void bindView(Condominio condominio) {
        textNomeCondominio.setText(condominio.getNome());
        selectableView.setBackgroundColor(condominio.isSelected() ? context.getResources().getColor(R.color.selected_condominio) : Color.WHITE);
    }
}
