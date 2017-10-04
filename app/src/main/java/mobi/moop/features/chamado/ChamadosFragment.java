package mobi.moop.features.chamado;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Chamado;
import mobi.moop.model.rotas.RotaChamados;
import mobi.moop.model.rotas.impl.RotaChamadoImpl;

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
        loadChamados();
        return view;
    }

    private void setupRecyclerView() {
        recyclerChamados.setLayoutManager(new LinearLayoutManager(getContext()));
        chamadosAdapter = new ChamadosAdapter(chamados);
    }

    private void loadChamados() {
        rotaChamado.loadChamados(getContext(), this);
    }

    private void showCriarChamadoFragment() {
        ((ChamadoActivity) getActivity()).showCriarChamadoFragment();
    }

    @Override
    public void onChamadosRecebidos(List<Chamado> chamados) {

    }

    @Override
    public void onRecebimentoFail(String errorBody) {

    }
}
