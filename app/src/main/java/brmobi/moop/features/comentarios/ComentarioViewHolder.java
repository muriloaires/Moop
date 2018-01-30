package brmobi.moop.features.comentarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import brmobi.moop.features.utils.DateUtils;
import brmobi.moop.model.entities.Comentario;
import brmobi.moop.model.rotas.RetrofitSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import brmobi.moop.R;

/**
 * Created by murilo aires on 11/08/2017.
 */

public class ComentarioViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.textNome)
    TextView textNome;

    @BindView(R.id.textComentario)
    TextView textComentario;

    @BindView(R.id.textHoraComentario)
    TextView textHoraComentario;

    @OnLongClick(R.id.rootView)
    public boolean showOptions(View view) {
        ((ComentariosActivity) context).showOptions(getAdapterPosition());
        return true;
    }

    private Context context;

    public ComentarioViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(final Comentario comentario) {
        textComentario.setText(comentario.getTexto());
        textNome.setText(comentario.getPerfil().getNome());
        if (!comentario.getPerfil().getAvatar().equals("")) {
            Picasso.with(context).load(comentario.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(RetrofitSingleton.BASE_URL + comentario.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
                }
            });
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        textHoraComentario.setText(DateUtils.getDifference(new Date(), comentario.getCreatedAt()));
    }
}
