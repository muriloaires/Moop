package mobi.moop.features.condominio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
    private Unidade unidadeSelecionada;
    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();

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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UnidadesFragment fragment = new UnidadesFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("blocoId", bloco.getId());
        bundle.putLong("condominioId", condominioSelecionado.getId());
        fragment.setArguments(bundle);
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack("main");
        ft.commit();
    }

    public void setTitutlo(String titulo) {
        textEscolha.setText(titulo);
    }

    public void selectUnidade(Unidade unidade) {
        this.unidadeSelecionada = unidade;
        showDialogMoradorProprietario();

    }

    private void showDialogMoradorProprietario() {
        View view = getLayoutInflater().inflate(R.layout.dialog_morador_proprietario, null);
        final RadioButton rdMorador = (RadioButton) view.findViewById(R.id.rdMorador);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDialogConfirmacao(rdMorador.isChecked());
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle(R.string.qual_seu_perfil);
        builder.show();
    }

    private void showDialogConfirmacao(final boolean isMorador) {

        String perfil = isMorador ? "morador" : "proprietário";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.atencao))
                .setMessage("Deseja se cadastrar em " + condominioSelecionado.getNome() + ", Bloco " + blocoSeleionado.getNome() + ", unidade " + unidadeSelecionada.getNumero() + " como " + perfil + "?")
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registrarEmCondominio(isMorador);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void registrarEmCondominio(boolean isMorador) {
        rotaCondominio.registrarUnidade(this,unidadeSelecionada.getId(),!isMorador,isMorador,this );
    }

    @Override
    public void onPerfilRegistrado() {

    }

    @Override
    public void onRegistrationFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
