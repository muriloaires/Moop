package mobi.moop.features.condominio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.Bloco;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;

public class AddCondominioActivity extends AppCompatActivity implements RotaCondominio.RegistroUnidadeHandler {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textEscolha)
    TextView textEscolha;
    private Condominio condominioSelecionado;
    private Bloco blocoSeleionado;
    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_condominio);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createLoginDialog();
        showCondominiosFragment();

    }

    private void showCondominiosFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.placeholder, new CondominiosFragment());
        ft.commit();
    }

    private void createLoginDialog() {
        progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setTitle(getString(R.string.aguarde));
        progress.setMessage(getString(R.string.registrando_unidade));
    }

    public void showBlocosFragment(Condominio condominio) {
        this.condominioSelecionado = condominio;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BlocosFragment fragment = new BlocosFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("condominioId", condominio.getId());
        fragment.setArguments(bundle);
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack("main");
        ft.commit();
    }

    public void showUnidadesFragment(Bloco bloco) {
        this.blocoSeleionado = bloco;
        showDialogMoradorProprietario();
    }

    public void setTitutlo(String titulo) {
        textEscolha.setText(titulo);
    }


    private void showDialogMoradorProprietario() {
        View view = getLayoutInflater().inflate(R.layout.dialog_morador_proprietario, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view)
                .setTitle(R.string.qual_seu_perfil);
        final AlertDialog dialog = builder.create();
        final RadioButton rdMorador = (RadioButton) view.findViewById(R.id.rdMorador);
        final TextInputLayout inputLayoutUnidade = (TextInputLayout) view.findViewById(R.id.inputLayoutUnidade);
        final EditText inputUnidade = (EditText) view.findViewById(R.id.inputUnidade);
        final Button btnConfirmar = (Button) view.findViewById(R.id.btnConfirmar);
        final Button btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputUnidade.getText().toString().equals("")) {
                    inputLayoutUnidade.setError(getString(R.string.campo_obrigatorio));
                } else {
                    showDialogConfirmacao(rdMorador.isChecked(), inputUnidade.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogConfirmacao(final boolean isMorador, final String unidade) {

        String perfil = isMorador ? "morador" : "proprietário";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.atencao))
                .setMessage("Deseja se cadastrar em " + condominioSelecionado.getNome() + ", Bloco " + blocoSeleionado.getNome() + ", unidade " + unidade + " como " + perfil + "?")
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registrarEmCondominio(isMorador, unidade);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void registrarEmCondominio(boolean isMorador, String unidade) {
        progress.show();
        rotaCondominio.registrarUnidade(this, blocoSeleionado.getId(), !isMorador, isMorador, unidade, this);
    }

    @Override
    public void onPerfilRegistrado() {
        progress.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRegistrationFail(String error) {
        progress.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
