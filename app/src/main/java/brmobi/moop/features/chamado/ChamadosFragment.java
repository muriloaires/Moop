package brmobi.moop.features.chamado;


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

import brmobi.moop.model.entities.Chamado;
import brmobi.moop.model.rotas.RotaChamados;
import brmobi.moop.model.rotas.impl.RotaChamadoImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChamadosFragment extends Fragment implements RotaChamados.RecebimentoChamadoHandler {

    @OnClick(R.id.btn_criar_chamado)
    public void btnCriarChamadoAction(View view) {
        showCriarChamadoFragment();
    }

    @BindView(R.id.recycler_chamados)
    RecyclerView recyclerChamados;

    @BindView(R.id.nenhumChamado)
    View nenhumChamado;

    private RotaChamadoImpl rotaChamado = new RotaChamadoImpl();
    private List<Chamado> chamados = new ArrayList<>();
    private ChamadosAdapter chamadosAdapter;

    public ChamadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chamado, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChamados();
    }

    @Override
    public void onStop() {
        super.onStop();
        rotaChamado.cancelLoadChamadosRequisition();
    }

    private void setupRecyclerView() {
        recyclerChamados.setLayoutManager(new LinearLayoutManager(getContext()));
        chamadosAdapter = new ChamadosAdapter(chamados);
        recyclerChamados.setAdapter(chamadosAdapter);
    }

    private void loadChamados() {
        rotaChamado.loadChamados(getContext(), this);
    }

    private void showCriarChamadoFragment() {
        ((ChamadoActivity) getActivity()).showCriarChamadoFragment();
    }

    @Override
    public void onChamadosRecebidos(List<Chamado> chamados) {
        if (chamados.size() == 0) {
            showNenhumChamadoView();
        } else {
            showLista();
        }
        this.chamados.clear();
        this.chamados.addAll(chamados);
        chamadosAdapter.notifyDataSetChanged();
    }

    private void showLista() {
        recyclerChamados.setVisibility(View.VISIBLE);
        nenhumChamado.setVisibility(View.GONE);
    }

    private void showNenhumChamadoView() {
        recyclerChamados.setVisibility(View.GONE);
        nenhumChamado.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecebimentoFail(String errorBody) {
        Toast.makeText(getContext(), errorBody, Toast.LENGTH_SHORT).show();
    }
}