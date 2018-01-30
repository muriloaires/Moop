package brmobi.moop.features.condominio;

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

import brmobi.moop.R;
import brmobi.moop.model.entities.Bloco;
import brmobi.moop.model.entities.CadastroCondominio;
import brmobi.moop.model.rotas.RotaCondominio;
import brmobi.moop.model.rotas.impl.RotaCondominioImpl;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCondominioActivity extends AppCompatActivity implements RotaCondominio.RegistroUnidadeHandler {

    private static final String TAG_REGISTRO_CONDOMINIO = "registroCondominio";
    private static final String TAG_REGISTRO_BLOCOS = "registroBlocos";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textEscolha)
    TextView textEscolha;
    private String condominioSelecionado;
    private Bloco blocoSeleionado;
    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private ProgressDialog progress;
    private String cep;
    private String nomeCondominio;
    private String endereco;
    private String numero;
    private String telefone;
    private boolean isHorizontal = false;

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

    @Override
    protected void onStop() {
        super.onStop();
        rotaCondominio.cancelRegistrarUnidadeRequisition();
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

    public void showBlocosFragment(Long condominioId, String condominioNome) {
        this.condominioSelecionado = condominioNome;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        BlocosFragment fragment = new BlocosFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("condominioId", condominioId);
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
                .setMessage("Deseja se cadastrar em " + condominioSelecionado + ", Bloco " + blocoSeleionado.getNome() + ", unidade " + unidade + " como " + perfil + "?")
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

    public void showRegistroCondominioFragment(String cep) {
        RegistroCondominioFragment fragment = new RegistroCondominioFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cep", cep);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, fragment, TAG_REGISTRO_CONDOMINIO);
        ft.addToBackStack("main");
        ft.commit();
    }


    public void showAddBlocoFragment(String cep, String nomeCondominio, String endereco, String numero, String telefone, boolean isHorizontal) {
        this.cep = cep;
        this.nomeCondominio = nomeCondominio;
        this.endereco = endereco;
        this.numero = numero;
        this.telefone = telefone;
        this.isHorizontal = isHorizontal;

        AddBlocoFragment fragment = new AddBlocoFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, fragment, TAG_REGISTRO_BLOCOS);
        ft.addToBackStack("main");
        ft.commit();
    }

    public String getNomeCondominio() {
        return nomeCondominio;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void onCondominioCadastrado(CadastroCondominio body) {
        onBackPressed();
        onBackPressed();
        showBlocosFragment(body.getId(), body.getNome());
    }
}
