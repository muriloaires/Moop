package mobi.moop.features.moradores;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.PerfilHabitacional;
import mobi.moop.model.rotas.RotaMoradores;
import mobi.moop.model.rotas.impl.RotaMoradoresImpl;

public class AprovarMoradoresActivity extends AppCompatActivity implements RotaMoradores.LiberarMoradoresHandler {

    @BindView(R.id.recyclerMoradores)
    RecyclerView recyclerMoradores;

    private RotaMoradoresImpl rotaMoradores = new RotaMoradoresImpl();
    private List<PerfilHabitacional> moradores = new ArrayList<>();
    private AprovarMoradoresAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovar_moradores);
        ButterKnife.bind(this);
        setupRecyclerView();
        createLoginDialog();
        loadMoradores();

    }

    private void createLoginDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.aguarde));
        progressDialog.setMessage(getString(R.string.efetuando_requisicao));
    }

    @Override
    protected void onStop() {
        super.onStop();
        rotaMoradores.cancelGetMoradoresAprovarRequisition();
        rotaMoradores.cancelAprovarMoradorRequisition();
    }

    private void loadMoradores() {
        rotaMoradores.getMoradoresNaoLiberados(this, this);
    }

    private void setupRecyclerView() {
        recyclerMoradores.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AprovarMoradoresAdapter(moradores);
        recyclerMoradores.setAdapter(adapter);
    }

    @Override
    public void onMoradoresRecebidos(List<PerfilHabitacional> usuarios) {
        this.moradores.clear();
        this.moradores.addAll(usuarios);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoMoradoresFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPerfilAprovado(boolean aprovado, int adapterPosition) {
        progressDialog.dismiss();
        String msg = aprovado ? getString(R.string.perfil_aprovado_com_sucesso) : getString(R.string.perfil_reprovado_com_sucesso);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        moradores.remove(adapterPosition);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAprovacaoError(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void desaprovarMorador(final int adapterPosition) {
        final PerfilHabitacional perfilHabitacional = moradores.get(adapterPosition);
        String desejaAprovar = "Deseja rejeitar o(a) morador(a) " + perfilHabitacional.getPerfil().getNome() + " na unidade " + perfilHabitacional.getUnidadeHabitacional().getNumero().toString() + "?";
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(desejaAprovar)
                .setPositiveButton(R.string.aceitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        rotaMoradores.aceitarMorador(AprovarMoradoresActivity.this, perfilHabitacional.getId(), adapterPosition, false, AprovarMoradoresActivity.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    public void aprovarMorador(final int adapterPosition) {
        final PerfilHabitacional perfilHabitacional = moradores.get(adapterPosition);
        String desejaAprovar = "Deseja aceitar o(a) morador(a) " + perfilHabitacional.getPerfil().getNome() + " na unidade " + perfilHabitacional.getUnidadeHabitacional().getNumero().toString() + "?";
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(desejaAprovar)
                .setPositiveButton(R.string.aceitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        rotaMoradores.aceitarMorador(AprovarMoradoresActivity.this, perfilHabitacional.getId(), adapterPosition, true, AprovarMoradoresActivity.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}
