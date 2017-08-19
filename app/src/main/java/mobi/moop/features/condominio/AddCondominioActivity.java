package mobi.moop.features.condominio;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;

public class AddCondominioActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textEscolha)
    TextView textEscolha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_condominio);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        showCondominiosFragment();

    }

    private void showCondominiosFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.placeholder, new CondominiosFragment());
        ft.commit();
    }


    public void showBlocosFragment(Long condominioId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BlocosFragment fragment = new BlocosFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("condominioId", condominioId);
        fragment.setArguments(bundle);
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack("main");
        ft.commit();
    }

    public void showUnidadesFragment(Long blocoId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UnidadesFragment fragment = new UnidadesFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("blocoId", blocoId);
        fragment.setArguments(bundle);
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack("main");
        ft.commit();
    }

    public void setTitutlo(String titulo) {
        textEscolha.setText(titulo);
    }
}
