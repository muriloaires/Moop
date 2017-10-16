package mobi.moop.features.reserva;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.DisponibilidadeBem;
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisponibilidadesFragment extends Fragment implements RotaReservas.DisponibilidadesHandler, RotaReservas.ReservaHandler {

    @BindView(R.id.recycler_disponibilidades)
    RecyclerView recyclerDisponibilidades;

    private List<DisponibilidadeBem> disponibilidades = new ArrayList<>();
    private DisponibilidadesAdapter adapter;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private String data;
    private Long bemComumId;
    private ProgressDialog reservaDialog;

    public DisponibilidadesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getString("data");
        bemComumId = getArguments().getLong("bemId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disponibilidades, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        createreservaDialog();
        loadDisponibilidades();
        return view;
    }

    private void createreservaDialog() {
        reservaDialog = new ProgressDialog(getContext());
        reservaDialog.setIndeterminate(true);
        reservaDialog.setCancelable(false);
        reservaDialog.setTitle(getString(R.string.aguarde));
        reservaDialog.setMessage(getString(R.string.efetuando_reserva));
    }

    private void loadDisponibilidades() {
        rotaReservas.getDisponibilidades(getContext(), bemComumId, this, data);
    }

    private void setupRecyclerView() {
        recyclerDisponibilidades.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DisponibilidadesAdapter(disponibilidades, this);
        recyclerDisponibilidades.setAdapter(adapter);
    }

    public static DisponibilidadesFragment getInstance(long bemId, Date selectedDay) {
        DisponibilidadesFragment fragment = new DisponibilidadesFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("bemId", bemId);
        bundle.putString("data", new SimpleDateFormat("yyyy-MM-dd").format(selectedDay));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDisponilidadesRecebidas(List<DisponibilidadeBem> disponibilidades) {
        this.disponibilidades.clear();
        this.disponibilidades.addAll(disponibilidades);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoDisponibilidadesErro(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void reservarDisponibilidade(int adapterPosition) {
        reservaDialog.show();
        rotaReservas.efetuarReserva(getContext(), disponibilidades.get(adapterPosition).getId(), this);
    }

    @Override
    public void onReservasRecebidas(List<ReservaBemComum> reservas) {

    }

    @Override
    public void onRecebimentoReservasError(String errorr) {
        Toast.makeText(getContext(), errorr, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReservaEfetuada(ReservaBemComum reserva) {
        reservaDialog.dismiss();
        Toast.makeText(getContext(), getString(R.string.reserva_efetuada_com_sucesso), Toast.LENGTH_SHORT).show();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onError(String error) {
        reservaDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
