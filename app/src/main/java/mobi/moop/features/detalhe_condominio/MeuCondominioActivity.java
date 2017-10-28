package mobi.moop.features.detalhe_condominio;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.RotaUsuario;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.model.rotas.impl.RotaLoginImpl;
import mobi.moop.model.singleton.UsuarioSingleton;

public class MeuCondominioActivity extends AppCompatActivity implements RotaCondominio.DetalheCondominioHandler, RotaUsuario.GerarSenhaHandler {

    @OnClick(R.id.btnGerarSenha)
    public void btnGerarSenhaAction(View view) {
        gerarNovaSenha();
    }

    @OnClick(R.id.btnGerenciarCondominio)
    public void btnGerenciarCondominioAction(View view){
        openMoopSite();
    }

    @OnClick({R.id.senha_gerada,R.id.toque_para_copiar})
    public void copiarNovaSenha(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Senha", textSenhaGerada.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Senha copiada para área de transferência", Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.sindico_layout)
    View sindicoLayout;

    @BindView(R.id.senha_gerada)
    TextView textSenhaGerada;

    @BindView(R.id.toque_para_copiar)
    TextView textToqueParaCopiar;

    @BindView(R.id.text_nome)
    TextView textNome;

    @BindView(R.id.textLogradouro)
    TextView textLogradouro;

    @BindView(R.id.textCep)
    TextView textCep;

    @BindView(R.id.text_orientacao)
    TextView textOrientacao;

    @BindView(R.id.imgOrientacao)
    ImageView imgOrientacao;

    @BindView(R.id.text_email)
    TextView textEmail;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private RotaLoginImpl rotaLogin = new RotaLoginImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_condominio);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadDetalhe();
    }

    private void loadDetalhe() {
        rotaCondominio.getDetalheCondominio(this, this);
        sindicoLayout.setVisibility(CondominioRepository.I.getCondominio(this).getIsSindico() ? View.VISIBLE : View.GONE);
        textEmail.setText(UsuarioSingleton.I.getUsuarioLogado(this).getUser().getEmail());
    }

    private void gerarNovaSenha() {
        rotaLogin.gerarSenha(this, this);
    }

    private void openMoopSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_moop)));
        startActivity(browserIntent);
    }

    @Override
    public void onDetalheRecebido(Condominio condominio) {
        textNome.setText(condominio.getNome());
        textCep.setText("CEP: "+condominio.getCep());
        textLogradouro.setText("Logradouro: "+condominio.getLogradouro());
        textOrientacao.setText(condominio.getIsHorizontal() ? getString(R.string.horizontal) : getString(R.string.vertical));
        imgOrientacao.setImageResource(condominio.getIsHorizontal() ? R.drawable.ic_house : R.drawable.ic_predio);

    }

    @Override
    public void onSenhaGerada(String senha) {
        textSenhaGerada.setVisibility(View.VISIBLE);
        textToqueParaCopiar.setVisibility(View.VISIBLE);
        textSenhaGerada.setText(senha);
    }

    @Override
    public void onError(String error) {

    }
}
