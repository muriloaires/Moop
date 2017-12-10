package mobi.moop.features.mensagens;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.comentarios.ComentariosActivity;
import mobi.moop.model.entities.Comentario;
import mobi.moop.model.entities.Mensagem;
import mobi.moop.model.rotas.RotaMensagem;
import mobi.moop.model.rotas.impl.RotaMensagemImpl;
import mobi.moop.model.singleton.UsuarioSingleton;

public class MensagemActivity extends AppCompatActivity implements RotaMensagem.MensagemHandler {

    private List<Mensagem> mensagens;
    private MensagensAdapter mensagensAdapter;

    @BindView(R.id.toolbar_mensagens)
    Toolbar toolbarMensagens;

    private RotaMensagemImpl rotaMensagem = new RotaMensagemImpl();

    @OnClick(R.id.btnEnviarMensagem)
    public void enviarComentario(View view) {
        if (!TextUtils.isEmpty(edtMensagem.getText())) {
            rotaMensagem.postMensagem(this, UsuarioSingleton.I.getUsuarioLogado(this), getIntent().getLongExtra("usuarioDestinoId", -1), edtMensagem.getText().toString(), this);
        }
    }

    @BindView(R.id.edt_mensagem)
    EditText edtMensagem;

    @BindView(R.id.recycler_mensagem)
    RecyclerView recyclerMensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMensagens);
        toolbarMensagens.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Mensagens com " + getIntent().getStringExtra("usuarioDestinoNome"));
        setupRecyclerView();
        loadMensagens();

    }

    @Override
    protected void onStop() {
        super.onStop();
        rotaMensagem.cancelGetMensagensRequisition();
        rotaMensagem.cancelPostMensagemRequisition();
    }

    private void loadMensagens() {
        rotaMensagem.getMensagens(this, UsuarioSingleton.I.getUsuarioLogado(this), getIntent().getLongExtra("usuarioDestinoId", -1), this);
    }

    private void setupRecyclerView() {
        recyclerMensagem.setLayoutManager(new LinearLayoutManager(this));
        mensagens = new ArrayList<>();
        mensagensAdapter = new MensagensAdapter(mensagens);
        recyclerMensagem.setAdapter(mensagensAdapter);
    }


    @Override
    public void onMensagemEnviada(Mensagem mensagem) {
        mensagens.add(mensagens.size(), mensagem);
        mensagensAdapter.notifyDataSetChanged();
        edtMensagem.setText("");
    }

    @Override
    public void onEnvioMensagemError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMensagensRecebidas(List<Mensagem> mensagens) {
        Collections.reverse(mensagens);
        this.mensagens.clear();
        this.mensagens.addAll(mensagens);
        mensagensAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoMensagensError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApagarMensagemError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMensagemApagada(Mensagem mensagem) {
        mensagens.remove(mensagem);
        mensagensAdapter.notifyDataSetChanged();
    }

    public void showOptions(int adapterPosition) {
        Mensagem mensagem = mensagens.get(adapterPosition);
        Long usuarioLogadoId = UsuarioSingleton.I.getUsuarioLogado(this).getId();
        if (mensagem.getDePerfil().getId().equals(usuarioLogadoId)) {
            showDeleteDialog(mensagem);
        }
    }

    private void showDeleteDialog(final Mensagem mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_apagar_mensagem)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rotaMensagem.apagarMensagem((Context) MensagemActivity.this, mensagem, MensagemActivity.this);
                    }
                }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
