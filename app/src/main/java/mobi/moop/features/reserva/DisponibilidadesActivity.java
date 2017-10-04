package mobi.moop.features.reserva;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;

public class DisponibilidadesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidades);
        ButterKnife.bind(this);
        configureToolbar();

        showDescricaoFragment();
    }

    private void configureToolbar() {
        toolbar.setTitle("Reservas / " + getIntent().getStringExtra("bemComumNome"));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showDescricaoFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DescricaoBemComumFragment fragment = DescricaoBemComumFragment.getInstance(getIntent().getStringExtra("bemComumNome"), getIntent().getStringExtra("bemComumAvatar"), getIntent().getStringExtra("bemComumTermos"),getIntent().getLongExtra("bemId",-1));
        ft.replace(R.id.placeholder, fragment);
        ft.commit();
    }

    public void showCalendarioFragment(Long bemId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CalendarioBemComumFragment fragment = CalendarioBemComumFragment.getInstance(getIntent().getLongExtra("bemId",-1));
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack("main");
        ft.commit();
    }
}
