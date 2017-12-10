package mobi.moop.features.condominio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.CadastroCondominio;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.utils.Mask;
import okhttp3.ResponseBody;

/**
 * Created by murilo aires on 29/09/2017.
 */

public class RegistroCondominioFragment extends Fragment implements RotaCondominio.CadastroCondominioHandler {

    @BindView(R.id.chkHorizontal)
    CheckBox chkHoriontal;

    @BindView(R.id.edit_nome_condominio)
    EditText editNomeCondominio;

    @BindView(R.id.input_layout_nome_condominio)
    TextInputLayout inputLayoutNomeCondominio;

    @BindView(R.id.edit_cep)
    EditText editCep;

    @BindView(R.id.text_input_layout_cep)
    TextInputLayout textInputLayoutCEP;

    @BindView(R.id.edit_endereco)
    EditText editEndereco;

    @BindView(R.id.text_input_layout_endereco)
    TextInputLayout textInputLayoutEndereco;

    @BindView(R.id.edit_numero)
    EditText editNumero;

    @BindView(R.id.text_input_layout_numero)
    TextInputLayout textInputLayoutNumero;

    @BindView(R.id.edit_telefone)
    EditText editTelefone;

    @BindView(R.id.text_input_layout_telefone)
    TextInputLayout textInputLayoutTelefone;

    private ProgressDialog cadastroDialog;

    @OnClick(R.id.btn_cadastrar_condominio)
    public void btnCadastrarCondominioAction(View view) {
        if (validate()) {

            ((AddCondominioActivity) getContext()).showAddBlocoFragment(editCep.getText().toString(), editNomeCondominio.getText().toString(), editEndereco.getText().toString(), editNumero.getText().toString(), editTelefone.getText().toString(), chkHoriontal.isChecked());
        }
    }

    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_condominio, container, false);
        ButterKnife.bind(this, view);
        editCep.addTextChangedListener(Mask.insert("##.###-###", editCep));
        createcadastroDialog();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AddCondominioActivity) getContext()).setTitutlo(getString(R.string.novo_condominio));
    }

    private void createcadastroDialog() {
        cadastroDialog = new ProgressDialog(getContext());
        cadastroDialog.setIndeterminate(true);
        cadastroDialog.setCancelable(false);
        cadastroDialog.setTitle(getString(R.string.aguarde));
        cadastroDialog.setMessage(getString(R.string.cadastrando_condominio));
    }

    private boolean validate() {
        if (editCep.getText().toString().equals("")) {
            textInputLayoutCEP.setError(getString(R.string.campo_obrigatorio));
            return false;
        }

        if (editCep.getText().toString().length() < 10) {
            textInputLayoutCEP.setError(getString(R.string.cep_invalido));
            return false;
        }

        if (editNomeCondominio.getText().toString().equals("")) {
            inputLayoutNomeCondominio.setError(getString(R.string.campo_obrigatorio));
            return false;
        }

        if (editEndereco.getText().toString().equals("")) {
            textInputLayoutEndereco.setError(getString(R.string.campo_obrigatorio));
            return false;
        }

        if (editNumero.getText().toString().equals("")) {
            textInputLayoutNumero.setError(getString(R.string.campo_obrigatorio));
            return false;
        }

        if (editTelefone.getText().toString().equals("")) {
            textInputLayoutTelefone.setError(getString(R.string.campo_obrigatorio));
            return false;
        }

        return true;
    }

    private void registrarCondominio() {
        cadastroDialog.show();
        rotaCondominio.cadastrarCondominio(getContext(), editCep.getText().toString(), editNomeCondominio.getText().toString(), editEndereco.getText().toString(), editNumero.getText().toString(), editTelefone.getText().toString(), chkHoriontal.isChecked(), this);
    }

    @Override
    public void onCondominioCadastrado(CadastroCondominio body) {
        cadastroDialog.dismiss();
    }

    @Override
    public void onCadastroError(String error) {
        cadastroDialog.dismiss();
    }
}
