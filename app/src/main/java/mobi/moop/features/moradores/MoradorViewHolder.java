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
import mobi.moop.model.entities.Usuario;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class MoradorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.text_nome)
    TextView textNome;

    private Usuario morador;

    @OnClick(R.id.btn_enviar_mensagem)
    public void btnEnviarMensagemAction(View view) {
        Intent intent = new Intent(context, MensagemActivity.class);
        intent.putExtra("usuarioDestinoId", morador.getId());
        intent.putExtra("usuarioDestinoNome", morador.getNome());
        context.startActivity(intent);
    }

    private final Context context;

    public MoradorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Usuario morador) {
        this.morador = morador;
        if (morador.getAvatar() != null && !morador.getAvatar().equals("")) {
            Picasso.with(context).load(morador.getAvatar()).placeholder(R.drawable.placeholder_avatar).error(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        textNome.setText(morador.getNome());
    }
}
