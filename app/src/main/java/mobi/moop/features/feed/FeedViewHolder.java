package mobi.moop.features.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
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
        Picasso.with(context).load(feedItem.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(RetrofitSingleton.BASE_URL +FeedViewHolder.this.feedItem.getPerfil().getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
            }
        });
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

            Picasso.with(context).load(RetrofitSingleton.BASE_URL + feedItem.getImagem())
                    .noFade()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imgPost, new Callback() {
                        @Override
                        public void onSuccess() {
                            try {
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context).load(RetrofitSingleton.BASE_URL + FeedViewHolder.this.feedItem.getImagem())
                                    .noFade()
                                    .into(imgPost, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            try {
                                                adapter.notifyDataSetChanged();
                                            } catch (Exception e) {

                                            }
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }
                    });
        }
    }
}
