package mobi.moop.features.comentarios;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Comentario;
import mobi.moop.model.rotas.RotaComentarios;
import mobi.moop.model.rotas.impl.RotaComentariosImpl;

public class ComentariosActivity extends AppCompatActivity implements RotaComentarios.ComentariosHandler {

    private List<Comentario> comentarios;
    private ComentariosAdapter comentariosAdapter;

    @BindView(R.id.toolbarComentarios)
    Toolbar toolbarComentarios;

    @OnClick(R.id.btnEnviarComentario)
    public void enviarComentario(View view) {
        if (!TextUtils.isEmpty(edtComentario.getText())) {
            rotaComentarios.postComentario(this, getIntent().getLongExtra("feedId", -1), edtComentario.getText().toString(), this);
        }
    }

    @BindView(R.id.edtComentario)
    EditText edtComentario;

    @BindView(R.id.recyclerComentarios)
    RecyclerView recyclerComentarios;

    RotaComentariosImpl rotaComentarios = new RotaComentariosImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarComentarios);
        toolbarComentarios.setNavigationIcon(R.drawable.ic_back);
        toolbarComentarios.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupRecyclerView();
        loadComentarios();

    }

    @Override
    protected void onStop() {
        super.onStop();
        rotaComentarios.cancelPostComentarioRequisition();
        rotaComentarios.cancelLoadComentariosRequisition();
    }

    private void loadComentarios() {
        rotaComentarios.loadComentarios((Context) this, getIntent().getLongExtra("feedId", -1), (RotaComentarios.ComentariosHandler) this);
    }

    private void setupRecyclerView() {
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(this));
        comentarios = new ArrayList<Comentario>();
        comentariosAdapter = new ComentariosAdapter(comentarios);
        recyclerComentarios.setAdapter(comentariosAdapter);
    }

    @Override
    public void onComentarioEnviado(Comentario comentario) {
        comentarios.add(comentarios.size(), comentario);
        comentariosAdapter.notifyDataSetChanged();
        edtComentario.setText("");
    }

    @Override
    public void onEnvioComentarioError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComentariosRecebidos(List<Comentario> comentarios) {
        this.comentarios.clear();
        this.comentarios.addAll(comentarios);
        comentariosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoComentariosError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
