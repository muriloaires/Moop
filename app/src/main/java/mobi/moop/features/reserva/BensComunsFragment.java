package mobi.moop.features.reserva;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

public class BensComunsFragment extends Fragment implements RotaReservas.BemComunHandler {

    @BindView(R.id.recyclerBensComuns)
    RecyclerView recyclerBensComuns;

    @BindView(R.id.autorizacaoView)
    View autorizacaoView;

    private List<BemComum> bensComuns = new ArrayList<>();
    private Condominio condominioSelecionado;
    private Context context;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private BensComunsAdapter adapter;

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
        loadCondominioSelecionado();
        loadBens();
        return view;
    }

    private void setupRecyclerView() {
        recyclerBensComuns.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BensComunsAdapter(bensComuns);
        recyclerBensComuns.setAdapter(adapter);
    }

    private void loadCondominioSelecionado() {
        this.condominioSelecionado = CondominioRepository.I.getCondominio(context);
    }

    private void loadBens() {
        if (condominioSelecionado.getIsLiberado()) {
            showRecycler();
            rotaReservas.getBensComund(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
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
        if (bensComuns.size() > 0) {
            this.bensComuns.clear();
            this.bensComuns.add(null);
            this.bensComuns.addAll(bensComuns);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecebimentoBensComunsErro(String erro) {
        Toast.makeText(context, erro, Toast.LENGTH_SHORT).show();
    }
}
