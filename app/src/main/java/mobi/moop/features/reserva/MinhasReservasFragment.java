package mobi.moop.features.reserva;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasReservasFragment extends Fragment implements RotaReservas.ReservaHandler, RotaReservas.CancelarReservaHandler {

    @BindView(R.id.recycler_reservas)
    RecyclerView recyclerReservas;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<ReservaBemComum> reservas = new ArrayList<>();
    private ReservasAdapter reservasAdapter;
    private ReservasFragment.PagerAdapter pagerAdater;

    public MinhasReservasFragment() {
        // Required empty public constructor
    }

    private RotaReservasImpl rotaReservas = new RotaReservasImpl();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minhas_reservas, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadReservas();
            }
        });
        refresh.setColorSchemeResources(R.color.colorPrimary);
        loadReservas();
        return view;
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
        rotaReservas.cancelGetReservasRequisition();
        rotaReservas.cancelCancelarReservaRequisition();
    }

    private void setupRecyclerView() {
        recyclerReservas.setLayoutManager(new LinearLayoutManager(getContext()));
        reservasAdapter = new ReservasAdapter(reservas, this);
        recyclerReservas.setAdapter(reservasAdapter);
    }

    public void loadReservas() {
        rotaReservas.getReservas(getContext(), CondominioPreferences.I.getLastSelectedCondominio(getContext()), this);
    }

    @Override
    public void onReservasRecebidas(List<ReservaBemComum> reservas) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        this.reservas.clear();
        this.reservas.addAll(reservas);
        reservasAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoReservasError(String errorr) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        Toast.makeText(getContext(), errorr, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReservaEfetuada(ReservaBemComum reserva) {

    }

    @Override
    public void onError(String error) {

    }

    public void cancelarReserva(final int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_cancelar_reserva)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReservaBemComum reservaBemComum = reservas.get(adapterPosition);
                        rotaReservas.cancelarReserva(getContext(), reservaBemComum.getId(), MinhasReservasFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    @Override
    public void onReservaCancelada() {
        loadReservas();
        Toast.makeText(getContext(), getString(R.string.reserva_cancelada_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelamentoError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void setPagerAdater(ReservasFragment.PagerAdapter pagerAdater) {
        this.pagerAdater = pagerAdater;
    }
}
