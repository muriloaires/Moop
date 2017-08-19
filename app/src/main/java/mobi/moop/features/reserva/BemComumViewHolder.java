package mobi.moop.features.reserva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.MoopActivity;
import mobi.moop.model.entities.BemComum;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BemComumViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.bemComumNome)
    TextView textBemComumNome;
    private BemComum bemComum;

    @OnClick(R.id.rootView)
    public void selectBemComum(View view) {
        Long bemComumId = bemComum.getId();
        ((MoopActivity) context).showDatePicker(bemComumId);
    }

    private Context context;

    public BemComumViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(BemComum bemComum) {
        this.bemComum = bemComum;
        textBemComumNome.setText(bemComum.getNome());
    }
}
