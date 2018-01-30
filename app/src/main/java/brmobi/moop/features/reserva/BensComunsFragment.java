package brmobi.moop.features.reserva;

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

import brmobi.moop.features.condominio.CondominioPreferences;
import brmobi.moop.model.entities.BemComum;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.repository.CondominioRepository;
import brmobi.moop.model.rotas.RotaReservas;
import brmobi.moop.model.rotas.impl.RotaReservasImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import brmobi.moop.R;

public class BensComunsFragment extends Fragment implements RotaReservas.BemComunHandler {

    @BindView(R.id.recyclerBensComuns)
    RecyclerView recyclerBensComuns;

    @BindView(R.id.autorizacaoView)
    View autorizacaoView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private List<BemComum> bensComuns = new ArrayList<>();
    private Condominio condominioSelecionado;
    private Context context;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private BensComunsAdapter adapter;
    private ReservasFragment.PagerAdapter pagerAdapter;

    public BensComunsFragment() {
        // Required empty public constructor
    }

    public static BensComunsFragment newInstance() {
        BensComunsFragment fragment = new BensComunsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bens_comuns, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBens();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        loadCondominioSelecionado();
        loadBens();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            refreshLayout.destroyDrawingCache();
            refreshLayout.clearAnimation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        rotaReservas.cancelGetBensComunsRequisition();
    }

    private void setupRecyclerView() {
        recyclerBensComuns.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BensComunsAdapter(bensComuns, this);
        recyclerBensComuns.setAdapter(adapter);
    }

    private void loadCondominioSelecionado() {
        this.condominioSelecionado = CondominioRepository.I.getCondominio(context);
    }

    private void loadBens() {
        if (condominioSelecionado.getIsLiberado()) {
            showRecycler();
            rotaReservas.getBensComuns(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
        } else {
            showNaoLiberadoView();
        }
    }

    private void showRecycler() {
        recyclerBensComuns.setVisibility(View.VISIBLE);
        autorizacaoView.setVisibility(View.GONE);
    }

    private void showNaoLiberadoView() {
        recyclerBensComuns.setVisibility(View.GONE);
        autorizacaoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBensComunsRecebidos(List<BemComum> bensComuns) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (bensComuns.size() > 0) {
            this.bensComuns.clear();
            this.bensComuns.add(null);
            this.bensComuns.addAll(bensComuns);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecebimentoBensComunsErro(String erro) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        Toast.makeText(context, erro, Toast.LENGTH_SHORT).show();
    }

    public void setPagerAdapter(ReservasFragment.PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    public void openDispobinilidadeActivity(BemComum bemComum) {
        pagerAdapter.openDispobinilidadeActivity(bemComum);
    }
}
