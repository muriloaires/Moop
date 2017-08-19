package mobi.moop.features.reserva;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.DisponibilidadeBem;
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

public class DisponibilidadesActivity extends AppCompatActivity implements RotaReservas.DisponibilidadesHandler, RotaReservas.ReservaHandler {

    @BindView(R.id.recyclerDisponibilidades)
    RecyclerView recyclerDisponibilidades;

    private String data;
    private Long bemId;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private List<DisponibilidadeBem> disponibilidades = new ArrayList<>();
    private DisponibilidadesAdapter disponibilidadesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidades);
        ButterKnife.bind(this);
        this.data = getIntent().getStringExtra("data");
        this.bemId = getIntent().getLongExtra("bemId", -1);
        setupRecyclerView();
        loadDisponibilidades();
    }

    private void loadDisponibilidades() {
        rotaReservas.getDisponibilidades(this, bemId, this, data);
    }

    private void setupRecyclerView() {
        recyclerDisponibilidades.setLayoutManager(new LinearLayoutManager(this));
        disponibilidadesAdapter = new DisponibilidadesAdapter(disponibilidades);
        recyclerDisponibilidades.setAdapter(disponibilidadesAdapter);
    }

    @Override
    public void onDisponilidadesRecebidas(List<DisponibilidadeBem> disponibilidades) {
        this.disponibilidades.addAll(disponibilidades);
        disponibilidadesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoDisponibilidadesErro(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void efetuarReserva(int adapterPosition) {
        rotaReservas.efetuarReserva(this, disponibilidades.get(adapterPosition).getId(), this.data, this);
    }

    @Override
    public void onReservaEfetuada(ReservaBemComum reservaBemComum) {

    }

    @Override
    public void onError(String error) {

    }
}
