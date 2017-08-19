package mobi.moop.features.condominio;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class UnidadesFragment extends Fragment implements RotaCondominio.UnidadesHandler {

    @BindView(R.id.recyclerUnidades)
    RecyclerView recyclerUnidades;

    List<Unidade> unidades = new ArrayList<>();

    private UnidadesAdapter unidadesAdapter;
    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unidades, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadUnidades();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AddCondominioActivity) getContext()).setTitutlo(getString(R.string.qual_sua_unidade));
    }

    private void setupRecyclerView() {
        recyclerUnidades.setLayoutManager(new LinearLayoutManager(getContext()));
        unidadesAdapter = new UnidadesAdapter(unidades);
        recyclerUnidades.setAdapter(unidadesAdapter);

    }

    private void loadUnidades() {
        rotaCondominio.getUnidadeBlocos(getContext(), getArguments().getLong("condominioId"), getArguments().getLong("blocoId"), this);
    }

    @Override
    public void onUnidadesRecebidas(List<Unidade> unidades) {
        this.unidades.clear();
        this.unidades.addAll(unidades);
        unidadesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetUnidadesError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
