package mobi.moop.features.moradores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.mensagens.MensagemActivity;
import mobi.moop.model.entities.PerfilHabitacional;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class MoradorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.text_nome)
    TextView textNome;

    @BindView(R.id.text_unidade_bloco)
    TextView textUnidadeBloco;

    private PerfilHabitacional morador;

    @OnClick(R.id.btn_enviar_mensagem)
    public void btnEnviarMensagemAction(View view) {
        Intent intent = new Intent(context, MensagemActivity.class);
        intent.putExtra("usuarioDestinoId", morador.getId());
        intent.putExtra("usuarioDestinoNome", morador.getPerfil().getNome());
        context.startActivity(intent);
    }

    private final Context context;

    public MoradorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(PerfilHabitacional morador) {
        this.morador = morador;
        if (morador.getPerfil().getAvatar() != null && !morador.getPerfil().getAvatar().equals("")) {
            Picasso.with(context).load(morador.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).error(R.drawable.placeholder_avatar).into(imgAvatar);
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        textNome.setText(morador.getPerfil().getNome());
        textUnidadeBloco.setText("Bloco " + morador.getUnidadeHabitacional().getBloco().getNome() + " Unidade " + morador.getUnidadeHabitacional().getNumero());
    }
}
