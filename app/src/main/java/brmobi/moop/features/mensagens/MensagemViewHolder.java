package brmobi.moop.features.mensagens;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import brmobi.moop.features.utils.DateUtils;
import brmobi.moop.model.entities.Mensagem;
import brmobi.moop.model.rotas.RetrofitSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import brmobi.moop.R;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class MensagemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.textNome)
    TextView textNome;

    @BindView(R.id.textMensagem)
    TextView textMensagem;

    @BindView(R.id.textHoraMensagem)
    TextView textHoraMensagem;

    @OnLongClick(R.id.rootView)
    public boolean showOptions(View view) {
        ((MensagemActivity) context).showOptions(getAdapterPosition());
        return true;
    }


    private Context context;

    public MensagemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(final Mensagem mensagem) {
        textMensagem.setText(mensagem.getMensagem());
        textNome.setText(mensagem.getDePerfil().getNome());
        if (!mensagem.getDePerfil().getAvatar().equals("")) {
            Picasso.with(context).load(mensagem.getDePerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(RetrofitSingleton.BASE_URL + mensagem.getDePerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
                }
            });
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        textHoraMensagem.setText(DateUtils.getDifference(new Date(), mensagem.getCreatedAt()));
    }
}