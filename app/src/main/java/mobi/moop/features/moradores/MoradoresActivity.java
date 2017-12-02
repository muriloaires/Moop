package mobi.moop.features.moradores;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.PerfilHabitacional;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RotaMoradores;

public class MoradoresActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RotaMoradores.MoradoresHandler {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private Condominio condominioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moradores);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        this.condominioSelecionado = CondominioRepository.I.getCondominio(this);
        getSupportActionBar().setTitle(condominioSelecionado.getNome() + " / " + "Moradores");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_back);
        showMoradoresFragment();
    }

    private void showMoradoresFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new MoradoresFragment());
        ft.commit();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onMoradoresRecebidos(List<PerfilHabitacional> usuarios) {
        Log.d("", "");
    }

    @Override
    public void onRecebimentoMoradoresFail(String error) {

    }
}
