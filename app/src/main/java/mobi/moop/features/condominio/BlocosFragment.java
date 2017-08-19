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
import mobi.moop.model.entities.Bloco;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class BlocosFragment extends Fragment implements RotaCondominio.BlocosHandler {

    @BindView(R.id.recyclerBlocos)
    RecyclerView recyclerBlocos;

    private List<Bloco> blocos = new ArrayList<>();
    private BlocosAdapter adapter;

    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocos, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadBlocos();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AddCondominioActivity) getContext()).setTitutlo(getString(R.string.agora_escolha_bloco));
    }

    private void loadBlocos() {
        rotaCondominio.getBlocosCondominio(getContext(), getArguments().getLong("condominioId"), this);
    }

    private void setupRecyclerView() {
        recyclerBlocos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BlocosAdapter(blocos);
        recyclerBlocos.setAdapter(adapter);
    }

    @Override
    public void onBlocosRecebidos(List<Bloco> blocos) {
        this.blocos.clear();
        this.blocos.addAll(blocos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetBlocosFail(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
