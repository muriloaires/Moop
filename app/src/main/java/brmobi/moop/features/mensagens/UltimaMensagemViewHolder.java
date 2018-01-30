package brmobi.moop.features.mensagens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import brmobi.moop.features.utils.DateUtils;
import brmobi.moop.model.entities.Mensagem;
import brmobi.moop.model.entities.Usuario;
import brmobi.moop.model.rotas.RetrofitSingleton;
import brmobi.moop.model.singleton.UsuarioSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class UltimaMensagemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.textNome)
    TextView textNome;

    @BindView(R.id.textMensagem)
    TextView textMensagem;

    @BindView(R.id.textHoraMensagem)
    TextView textHoraMensagem;

    @OnClick(R.id.rootView)
    public void showMsgs(View view) {
        Intent intent = new Intent(context, MensagemActivity.class);
        intent.putExtra("usuarioDestinoId", outroUsuario.getId());
        intent.putExtra("usuarioDestinoNome", outroUsuario.getNome());
        context.startActivity(intent);
    }

    private Usuario outroUsuario;

    private Context context;

    private boolean isMsmUsuarioLogado;
    String prefix = "";

    public UltimaMensagemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(final Mensagem mensagem) {
        isMsmUsuarioLogado = UsuarioSingleton.I.getUsuarioLogado(context).getId().equals(mensagem.getDePerfil().getId());
        prefix = isMsmUsuarioLogado ? "Você: " : "";
        outroUsuario = UsuarioSingleton.I.getUsuarioLogado(context).getId().equals(mensagem.getDePerfil().getId()) ? mensagem.getParaPerfil() : mensagem.getDePerfil();
        textMensagem.setText(prefix + mensagem.getMensagem());
        textNome.setText(outroUsuario.getNome());
        if (!outroUsuario.getAvatar().equals("")) {
            Picasso.with(context).load(outroUsuario.getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(RetrofitSingleton.BASE_URL + outroUsuario.getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
                }
            });
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        textHoraMensagem.setText(DateUtils.getDifferenceSmall(new Date(), mensagem.getCreatedAt()));
    }
}
