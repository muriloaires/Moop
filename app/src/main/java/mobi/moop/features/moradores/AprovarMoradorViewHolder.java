package mobi.moop.features.moradores;

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
import mobi.moop.model.entities.PerfilHabitacional;

/**
 * Created by murilo aires on 16/10/2017.
 */

public class AprovarMoradorViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.text_nome)
    TextView textNome;

    @BindView(R.id.text_unidade)
    TextView textUnidade;

    @OnClick(R.id.aprove)
    public void imgAproveAction(View view) {
        ((AprovarMoradoresActivity) context).aprovarMorador(getAdapterPosition());
    }

    @OnClick(R.id.desaprove)
    public void imgDesaproveAction(View view) {
        ((AprovarMoradoresActivity) context).desaprovarMorador(getAdapterPosition());
    }

    public AprovarMoradorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();

    }

    public void bindView(PerfilHabitacional perfil) {
        if (perfil.getPerfil().getAvatar() != null && !perfil.getPerfil().getAvatar().equals("")) {
            Picasso.with(context).load(perfil.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).error(R.drawable.placeholder_avatar).into(imgAvatar);
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }

        textNome.setText(perfil.getPerfil().getNome());
        textUnidade.setText("Unidade " + perfil.getUnidadeHabitacional().getNumero().toString());
    }
}
