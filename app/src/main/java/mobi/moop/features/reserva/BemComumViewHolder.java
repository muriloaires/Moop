package mobi.moop.features.reserva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.MoopActivity;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.rotas.RetrofitSingleton;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BemComumViewHolder extends RecyclerView.ViewHolder {
    private final BensComunsAdapter adapter;

    @BindView(R.id.bemComumNome)
    TextView textBemComumNome;

    @BindView(R.id.text_preco)
    TextView textPreco;

    @BindView(R.id.img_bem_comum)
    ImageView imgBemComum;

    private BemComum bemComum;

    @OnClick(R.id.rootView)
    public void selectBemComum(View view) {
        adapter.openDispobinilidadeActivity(bemComum);
    }

    private Context context;

    public BemComumViewHolder(View itemView, BensComunsAdapter adapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        this.adapter = adapter;
    }

    public void bindView(BemComum bemComum) {
        this.bemComum = bemComum;
        textBemComumNome.setText(bemComum.getNome());
        textPreco.setText(bemComum.getValorCurrency());
        if (bemComum.getAvatar() != null && !bemComum.getAvatar().equals("")) {
            Picasso.with(context).load(RetrofitSingleton.BASE_URL + bemComum.getAvatar());
        }
    }
}
