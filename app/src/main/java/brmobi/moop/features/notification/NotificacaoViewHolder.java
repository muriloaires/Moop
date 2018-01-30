package brmobi.moop.features.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import brmobi.moop.R;
import brmobi.moop.features.comentarios.ComentariosActivity;
import brmobi.moop.features.moradores.AprovarMoradoresActivity;
import brmobi.moop.features.utils.DateUtils;
import brmobi.moop.model.entities.Notificacao;
import brmobi.moop.model.rotas.RetrofitSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by murilo aires on 21/12/2017.
 */

public class NotificacaoViewHolder extends RecyclerView.ViewHolder {
    private final Context context;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.textNome)
    TextView textnome;

    @BindView(R.id.textAcao)
    TextView textAcao;

    @BindView(R.id.textHora)
    TextView textHora;

    @OnClick(R.id.rootView)
    public void openNotificacao(View view) {
        switch (notificacao.getTipo()) {
            case NotificationController.TIPO_NOVA_CURTIDA:
                Intent intent = new Intent(context, ComentariosActivity.class);
                intent.putExtra("feedId", notificacao.getIdObj());
                context.startActivity(intent);
                break;
            case NotificationController.TIPO_NOVO_COMENTARIO_FEED:
                Intent intent2 = new Intent(context, ComentariosActivity.class);
                intent2.putExtra("feedId", notificacao.getIdObj());
                context.startActivity(intent2);
                break;
            case NotificationController.TIPO_SOLICITACAO_MORADOR:
                Intent intent3 = new Intent(context, AprovarMoradoresActivity.class);
                context.startActivity(intent3);
                break;
        }
    }

    private Notificacao notificacao;

    public NotificacaoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Notificacao theNotificacao) {
        this.notificacao = theNotificacao;
        if (!notificacao.getPerfil().getAvatar().equals("")) {
            Picasso.with(context).load(notificacao.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(avatar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(RetrofitSingleton.BASE_URL + notificacao.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(avatar);
                }
            });
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(avatar);
        }
        textnome.setText(notificacao.getPerfil().getNome());
        textHora.setText(DateUtils.getDifference(new Date(), notificacao.getCreatedAt()));
        setAcaoText();
    }

    private void setAcaoText() {
        switch (notificacao.getTipo()) {
            case NotificationController.TIPO_NOVA_CURTIDA:
                textAcao.setText("Curtiu sua publicação");
                break;
            case NotificationController.TIPO_NOVO_COMENTARIO_FEED:
                textAcao.setText("Comentou em sua publicação: " + notificacao.getMensagem());
                break;
            case NotificationController.TIPO_SOLICITACAO_MORADOR:
                textAcao.setText(notificacao.getMensagem());
                break;
        }
    }
}
