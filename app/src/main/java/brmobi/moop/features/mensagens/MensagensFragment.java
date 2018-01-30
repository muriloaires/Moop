package brmobi.moop.features.mensagens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brmobi.moop.R;
import brmobi.moop.features.MoopActivity;
import brmobi.moop.features.viewutils.Scrollable;
import brmobi.moop.model.entities.Mensagem;
import brmobi.moop.model.rotas.RotaMensagem;
import brmobi.moop.model.rotas.impl.RotaMensagemImpl;
import brmobi.moop.model.singleton.UsuarioSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MensagensFragment extends Fragment implements Scrollable, RotaMensagem.UltimasMensagensHandler {

    @BindView(R.id.recycler_ultimas_mensagens)
    RecyclerView recyclerUltimasMensagens;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @OnClick(R.id.fab)
    public void onFabAction(View view) {
        ((MoopActivity) getActivity()).showMoradoresActivity();
    }

    private RotaMensagemImpl rotaMensagem = new RotaMensagemImpl();
    private UltimasMensagensAdapter ultimasMensagensAdapter;
    private List<Mensagem> ultimasMensagens = new ArrayList<>();

    public MensagensFragment() {
        // Required empty public constructor
    }

    public static MensagensFragment newInstance() {
        MensagensFragment fragment = new MensagensFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mensagens, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUltimasMensagens();
            }
        });
        refresh.setColorSchemeResources(R.color.colorPrimary);
        loadUltimasMensagens();
        return view;
    }

    public void loadUltimasMensagens() {
        rotaMensagem.getUltimasMensagens(getContext(), UsuarioSingleton.I.getUsuarioLogado(getContext()), this);
    }

    private void setupRecyclerView() {
        recyclerUltimasMensagens.setLayoutManager(new LinearLayoutManager(getContext()));
        ultimasMensagensAdapter = new UltimasMensagensAdapter(ultimasMensagens);
        recyclerUltimasMensagens.setAdapter(ultimasMensagensAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (refresh != null) {
            refresh.setRefreshing(false);
            refresh.destroyDrawingCache();
            refresh.clearAnimation();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        rotaMensagem.cancelGetUltimasMensagensRequisition();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void scrollToTop() {

    }

    @Override
    public void onUltimasMensagensRecebidas(List<Mensagem> ultimasMensagens) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        this.ultimasMensagens.clear();
        this.ultimasMensagens.addAll(ultimasMensagens);
        ultimasMensagensAdapter.notifyDataSetChanged();

    }

    @Override
    public void onUltimasMensagensError(String error) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
