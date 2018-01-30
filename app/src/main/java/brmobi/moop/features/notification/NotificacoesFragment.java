package brmobi.moop.features.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brmobi.moop.R;
import brmobi.moop.features.viewutils.Scrollable;
import brmobi.moop.model.entities.Notificacao;
import brmobi.moop.model.rotas.RotaNotificacoes;
import brmobi.moop.model.rotas.impl.RotaNotificacoesImpl;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificacoesFragment extends Fragment implements Scrollable, RotaNotificacoes.NotificacoesHandler {

    @BindView(R.id.recycler_notificacoes)
    RecyclerView recyclerView;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    public NotificacoesFragment() {
        // Required empty public constructor
    }

    public static NotificacoesFragment newInstance() {
        NotificacoesFragment fragment = new NotificacoesFragment();
        return fragment;
    }

    private List<Notificacao> notificacoes = new ArrayList<>();
    private RotaNotificacoesImpl rotaNotificacoes = new RotaNotificacoesImpl();
    private NotificacoesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacoes, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadNotificacoes();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNotificacoes();
            }
        });
        return view;
    }

    private void loadNotificacoes() {
        rotaNotificacoes.getNotificacoes(getContext(), this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificacoesAdapter(notificacoes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void scrollToTop() {

    }

    @Override
    public void onNotificacoesReceived(List<Notificacao> body) {
        this.notificacoes.clear();
        notificacoes.addAll(body);
        adapter.notifyDataSetChanged();
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onError(String errorBody) {

    }
}
