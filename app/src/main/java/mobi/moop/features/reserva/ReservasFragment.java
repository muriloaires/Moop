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
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservasFragment extends Fragment implements RotaReservas.BemComunHandler {

    @BindView(R.id.recyclerBensComuns)
    RecyclerView recyclerBensComuns;

    private List<BemComum> bensComuns = new ArrayList<>();
    private List<Object> reservas = new ArrayList<>();
    private List<Object> listaAdapter = new ArrayList<>();
    private BensComunsAdapter adapter;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private Context context;

    public ReservasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadBens();
        loadReservas();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void loadReservas() {
        rotaReservas.getReservas(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
    }

    private void loadBens() {
        rotaReservas.getBensComund(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
    }

    private void setupRecyclerView() {
        recyclerBensComuns.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BensComunsAdapter(listaAdapter);
        recyclerBensComuns.setAdapter(adapter);
    }

    @Override
    public void onBensComunsRecebidos(List<BemComum> bensComuns) {
        if (bensComuns.size() > 0) {
            this.bensComuns.add(null);
            this.bensComuns.addAll(bensComuns);
            this.listaAdapter.addAll(this.bensComuns);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecebimentoBensComunsErro(String erro) {
        Toast.makeText(context, erro, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReservasRecebidas(List<ReservaBemComum> reservas) {
        if (reservas.size() > 0) {
            this.reservas.add("HEADER");
            this.reservas.addAll(reservas);
            this.listaAdapter.addAll(0, this.reservas);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onRecebimentoReservasError(String errorr) {
        Toast.makeText(context, errorr, Toast.LENGTH_SHORT).show();
    }
}
