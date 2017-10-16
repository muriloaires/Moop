package mobi.moop.features.feed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.MoopApplication;
import mobi.moop.R;
import mobi.moop.features.comentarios.ComentariosActivity;
import mobi.moop.features.publicacoes.ImageActivity;
import mobi.moop.features.utils.DateUtils;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.RetrofitSingleton;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.textNome)
    TextView textNome;

    @BindView(R.id.textTexto)
    TextView textConteudo;

    @BindView(R.id.imgPost)
    ImageView imgPost;

    @BindView(R.id.textNumeroComments)
    TextView textNumeroComments;

    @BindView(R.id.textNumeroCurtidas)
    TextView textNumeroCurtidas;

    @BindView(R.id.textHoraComentario)
    TextView textHoraComentario;

    @BindView(R.id.imgCurtir)
    ImageView imgCurtir;

    @BindView(R.id.imgDescurtir)
    ImageView imgDiscurtir;

    @OnClick(R.id.imgCurtir)
    public void imgCurtirAction(View view) {
        curtir();
    }

    @OnClick(R.id.imgDescurtir)
    public void imgDIscurtirAction(View view) {
        descurtir();
    }

    @OnClick(R.id.imgPost)
    public void imgPostAction(View view) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("bitmap", feedItem.getImagem());
        context.startActivity(intent);
    }

    @OnClick(R.id.textNumeroComments)
    public void textNumeroCommentsAction(View view) {
        Intent intent = new Intent(context, ComentariosActivity.class);
        intent.putExtra("feedId", feedItem.getId());
        context.startActivity(intent);
    }

    private FeedItem feedItem;
    private FeedAdapter adapter;

    public FeedViewHolder(View itemView, FeedAdapter feedAdapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        this.adapter = feedAdapter;
    }

    public void bindView(FeedItem feedItem) {
        this.feedItem = feedItem;
        textNome.setText(feedItem.getPerfil().getNome());
        textHoraComentario.setText(DateUtils.getDifference(new Date(), feedItem.getData()));
        textNumeroComments.setText(" " + feedItem.getComentarios().toString());
        textNumeroCurtidas.setText(" " + feedItem.getCurtidas().toString());
        if (!feedItem.getPerfil().getAvatar().equals("")) {
            Picasso.with(context).load(feedItem.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(RetrofitSingleton.BASE_URL + FeedViewHolder.this.feedItem.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
                }
            });
        } else {
            Picasso.with(context).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }

        if (feedItem.getTexto() == null || feedItem.getTexto().equals("")) {
            textConteudo.setVisibility(View.GONE);
        } else {
            textConteudo.setVisibility(View.VISIBLE);
            textConteudo.setText(feedItem.getTexto());
        }

        if (feedItem.getImagem() == null || feedItem.getImagem().equals("")) {
            imgPost.setVisibility(View.GONE);
        } else {
            imgPost.setVisibility(View.VISIBLE);
            float constante = (float) MoopApplication.screenWidth / feedItem.getwImage();
            int imgViewWidth = (int) (constante * feedItem.getwImage());
            int imgViewHeight = (int) (constante * feedItem.gethImage());
            ViewGroup.LayoutParams params = imgPost.getLayoutParams();
            params.width = imgViewWidth;
            params.height = imgViewHeight;
            imgPost.setLayoutParams(params);
            Picasso.with(context).load(RetrofitSingleton.BASE_URL + feedItem.getImagem())
                    .noFade()
                    .placeholder(R.drawable.feedplaceholder)
                    .into(imgPost);
        }

        if (feedItem.isCurtidaPeloUsuario()) {
            imgDiscurtir.setVisibility(View.VISIBLE);
            imgCurtir.setVisibility(View.GONE);
        } else {
            imgDiscurtir.setVisibility(View.GONE);
            imgCurtir.setVisibility(View.VISIBLE);
        }
    }

    public void curtir() {
        adapter.curtir(getAdapterPosition());
    }

    public void descurtir() {
        adapter.descurtir(getAdapterPosition());
    }
}
