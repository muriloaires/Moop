package mobi.moop.features.condominio;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.CadastroCondominio;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBlocoFragment extends Fragment implements RotaCondominio.AddBlocoHandler {

    @BindView(R.id.textNomeCondominio)
    TextView textNomeCondominio;

    @BindView(R.id.textEnderecoCondominio)
    TextView textEnderecoCondominio;

    @BindView(R.id.textCepCondominio)
    TextView textCepCondominio;

    @BindView(R.id.textInputNomeBloco)
    TextInputLayout textInputNomeBloco;

    @BindView(R.id.editNomeBloco)
    EditText editNomeBloco;

    private ProgressDialog addBlocoDialog;

    @OnClick(R.id.btnAddBloco)
    public void btnAddBlocoAction(View view) {
        if (validate()) {
            cadastrarBloco();
        }
    }

    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();

    private CadastroCondominio condominioCadastrado;

    public AddBlocoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.condominioCadastrado = (CadastroCondominio) getArguments().getSerializable("condominio");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_bloco, container, false);
        ButterKnife.bind(this, view);
        populateFields();
        createcadastroDialog();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AddCondominioActivity) getContext()).setTitutlo(getString(R.string.add_bloco_condominio));
    }

    private void createcadastroDialog() {
        addBlocoDialog = new ProgressDialog(getContext());
        addBlocoDialog.setIndeterminate(true);
        addBlocoDialog.setCancelable(false);
        addBlocoDialog.setTitle(getString(R.string.aguarde));
        addBlocoDialog.setMessage(getString(R.string.cadastrando_bloco));
    }

    private void populateFields() {
        textNomeCondominio.setText(condominioCadastrado.getNome());
        textCepCondominio.setText(condominioCadastrado.getCep());
        textEnderecoCondominio.setText(condominioCadastrado.getLogradouro() + " - " + condominioCadastrado.getNumero());
    }

    private boolean validate() {
        if (editNomeBloco.getText().toString().equals("")) {
            textInputNomeBloco.setError(getString(R.string.campo_obrigatorio));
            return false;
        }
        return true;
    }

    private void cadastrarBloco() {
        addBlocoDialog.show();
        rotaCondominio.cadastrarBlocoCondominio(getContext(), condominioCadastrado.getId(), editNomeBloco.getText().toString(), "", this);
    }

    @Override
    public void onBlocoAdicionado(ResponseBody body) {
        addBlocoDialog.dismiss();
        Toast.makeText(getContext(), getString(R.string.bloco_add_com_sucesso), Toast.LENGTH_SHORT).show();
        editNomeBloco.setText("");
    }

    @Override
    public void onBlocoAddError(String error) {
        addBlocoDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
