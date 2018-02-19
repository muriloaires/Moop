package brmobi.moop.features.feed;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brmobi.moop.R;
import brmobi.moop.features.publicacoes.NewPostActivity;
import brmobi.moop.features.viewutils.Scrollable;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.entities.FeedItem;
import brmobi.moop.model.repository.CondominioRepository;
import brmobi.moop.model.rotas.RotaFeed;
import brmobi.moop.model.rotas.impl.RotaFeedImpl;
import brmobi.moop.utils.EndlessRecyclerOnScrollListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements RotaFeed.FeedHandler, Scrollable, RotaFeed.CurtidaHandler {

    private static final int WRITE_POST = 1;
    @BindView(R.id.recyclerFeed)
    RecyclerView recyclerView;

    @BindView(R.id.fab_createevent)
    FloatingActionButton fab;

    @BindView(R.id.refresh)
    SwipeRefreshLayout referesh;

    @BindView(R.id.autorizacaoView)
    View autorizacaoView;

    @BindView(R.id.nenhumaPublicacao)
    TextView textNenhumaPublicacao;

    private Context context;

    private Condominio condominioSelecionado;

    @OnClick(R.id.fab_createevent)
    public void fabAction(View view) {
        if (condominioSelecionado.getIsLiberado()) {
            writeNewPost();
        }
    }

    private List<FeedItem> items;
    private FeedAdapter feedAdapter;
    private RotaFeedImpl rotaFeed = new RotaFeedImpl();
    private EndlessRecyclerOnScrollListener scrollListener;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeed(0);
            }
        });
        referesh.setColorSchemeResources(R.color.colorPrimary);
        loadCondominioSelecionado();
        return view;
    }

    private void loadCondominioSelecionado() {
        condominioSelecionado = CondominioRepository.I.getCondominio(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFeed(items.size() - 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (referesh != null) {
            referesh.setRefreshing(false);
            referesh.destroyDrawingCache();
            referesh.clearAnimation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        rotaFeed.cancelGetFeedRequisition();
    }

    private void loadFeed(int offset) {
        if (condominioSelecionado.getIsLiberado()) {
            showRecycler();
            recyclerView.removeOnScrollListener(scrollListener);
            rotaFeed.getFeed(context, condominioSelecionado.getId(), 10, offset, this);
        } else {
            showCondominioNaoLiberado();
        }
    }

    private void showCondominioNaoLiberado() {
        recyclerView.setVisibility(View.GONE);
        autorizacaoView.setVisibility(View.VISIBLE);
    }

    private void showRecycler() {
        recyclerView.setVisibility(View.VISIBLE);
        autorizacaoView.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        items = new ArrayList<>();
        items.add(null);
        feedAdapter = new FeedAdapter(items, this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0 && fab.isShown()) {
                    fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                        @Override
                        public void onHidden(FloatingActionButton fab) {
                            fab.setVisibility(View.GONE);
                        }
                    });
                } else if (dy < 0) {
                    fab.show();
                }
            }


        });
        scrollListener = new EndlessRecyclerOnScrollListener(manager, 3) {
            @Override
            public void onLoadMore(int current_page) {
                loadFeed(items.size() - 1);
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onFeedReceived(List<FeedItem> items, int offset) {
        if (referesh.isRefreshing()) {
            referesh.setRefreshing(false);
        }
        scrollListener.resetPreviousTotal();
        if (offset == 0) {
            textNenhumaPublicacao.setVisibility(items.size() == 0 ? View.VISIBLE : View.GONE);
            this.items.clear();
            this.items.add(null);
        }
        this.items.addAll(this.items.size() - 1, items);
        if (items.size() < 10) {
            this.items.remove(this.items.size() - 1);
            feedAdapter.notifyDataSetChanged();
        } else {
            feedAdapter.notifyDataSetChanged();
            recyclerView.addOnScrollListener(scrollListener);

        }
    }

    @Override
    public void onFeedReceiveFail(String error) {
        if (referesh.isRefreshing()) {
            referesh.setRefreshing(false);
        }
        scrollListener.resetPreviousTotal();
        recyclerView.addOnScrollListener(scrollListener);
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUsuarioNaoLiberado() {

    }

    @Override
    public void onFeedApagado(FeedItem feedItem) {
        items.remove(feedItem);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onApagarFeedError(String errorBody) {

    }

    private void writeNewPost() {
        startActivityForResult(new Intent(context, NewPostActivity.class), WRITE_POST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case WRITE_POST:
                if (resultCode == Activity.RESULT_OK) {
                    loadFeed(0);
                }
                break;
        }
    }

    @Override
    public void scrollToTop() {
        try {
            recyclerView.smoothScrollToPosition(0);
        } catch (NullPointerException e) {

        }
    }

    public void curtir(int adapterPosition) {
        FeedItem feedItem = items.get(adapterPosition);
        feedItem.setCurtidaPeloUsuario(true);
        feedAdapter.notifyItemChanged(adapterPosition);
        rotaFeed.curtirFeed(getContext(), items.get(adapterPosition).getId(), this);
    }

    public void descurtir(int adapterPosition) {
        FeedItem feedItem = items.get(adapterPosition);
        feedItem.setCurtidaPeloUsuario(false);
        if (feedItem.getCurtidas() > 0) {
            feedItem.setCurtidas(feedItem.getCurtidas() - 1);
        }
        feedAdapter.notifyItemChanged(adapterPosition);
    }

    @Override
    public void onFeedCurtido(Long feedId, Integer curtidas) {
        FeedItem feedItem = getFeedItem(feedId);
        if (feedItem != null) {
            feedItem.setCurtidaPeloUsuario(true);
            feedItem.setCurtidas(curtidas);
            feedAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCurtirFeedFail(Long feedId, String error) {
        FeedItem feedItem = getFeedItem(feedId);
        if (feedItem != null) {
            feedItem.setCurtidaPeloUsuario(false);
            feedAdapter.notifyDataSetChanged();
        }
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeedDescurtido(Long feedId) {
        FeedItem feedItem = getFeedItem(feedId);
        if (feedItem != null) {
            feedItem.setCurtidaPeloUsuario(false);
            feedAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDescurtirFeedError(Long feedId, String error) {
        FeedItem feedItem = getFeedItem(feedId);
        if (feedItem != null) {
            feedItem.setCurtidaPeloUsuario(true);
            feedItem.setCurtidas(feedItem.getCurtidas() + 1);
            feedAdapter.notifyDataSetChanged();
        }
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    private FeedItem getFeedItem(Long feedId) {
        for (FeedItem feedItem : items) {
            if (feedItem.getId().equals(feedId)) {
                return feedItem;
            }
        }
        return null;
    }

    public void showOptions(int adapterPosition) {
        final FeedItem feedItem = items.get(adapterPosition);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_apagar_postagem)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rotaFeed.apagarFeed(getContext(), feedItem, FeedFragment.this);
                    }
                }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
