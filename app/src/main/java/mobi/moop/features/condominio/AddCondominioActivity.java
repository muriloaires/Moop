package mobi.moop.features.condominio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Bloco;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.utils.Mask;

public class AddCondominioActivity extends AppCompatActivity implements RotaCondominio.CondominiosHandler, RotaCondominio.BlocosHandler, RotaCondominio.UnidadesHandler, RotaCondominio.RegistroUnidadeHandler {

    @BindView(R.id.editCEP)
    EditText editCep;

    @BindView(R.id.spinnerCondominios)
    AppCompatSpinner spinnerCondominios;

    @BindView(R.id.spinnerBlocos)
    AppCompatSpinner spinnerBlocos;

    @BindView(R.id.spinnerUnidades)
    AppCompatSpinner spinnerUnidade;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.btnCadastrarCondominio)
    Button btnCadastrarCondominio;

    @BindView(R.id.rdMorador)
    RadioButton rdMorador;

    @BindView(R.id.rdProprietario)
    RadioButton rdProprietario;

    @OnClick(R.id.btnCadastrarCondominio)
    public void btnCadastrarAction(View view) {
        cadastrarUnidade();
    }

    RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private List<String> condominiosString;
    private List<String> unidadeString;

    private List<String> blocosString;
    private List<Condominio> condominios;
    private List<Bloco> blocos;

    private List<Unidade> unidades;
    private Condominio condominioSelecionado;
    private Unidade unidadeSelecionada;
    private Bloco blocoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_condominio);
        ButterKnife.bind(this);
        configureCepMask();
    }

    private void configureCepMask() {
        editCep.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;
            String old = "";
            String mask = "##.###-###";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = Mask.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editCep.setText(mascara);
                editCep.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable ed) {
                String s = ed.toString();
                if (s.length() == 10) {
                    getCondominiosCep(s);
                }
            }
        });
    }

    private void getCondominiosCep(String cep) {
        progress.setVisibility(View.VISIBLE);
        rotaCondominio.getCondominiosCep(this, cep, this);
    }


    private void populateSpinnerCondominios(List<Condominio> condominios) {
        this.condominiosString = new ArrayList<>();
        this.condominios = condominios;
        this.condominiosString.add(getString(R.string.condominios));
        for (Condominio condominio : condominios) {
            condominiosString.add(condominio.getNome());
        }
        ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(this, R.layout.text1, condominiosString);
        adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondominios.setAdapter(adapterBusinessType);
        spinnerCondominios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    position--;
                    condominioSelecionado = AddCondominioActivity.this.condominios.get(position);
                    loadBlocosCondominios();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinnerBlocos(List<Bloco> blocos) {
        this.blocosString = new ArrayList<>();
        this.blocos = blocos;
        this.blocosString.add(getString(R.string.blocos));
        for (Bloco bloco : blocos) {
            blocosString.add(bloco.getNome());
        }
        ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(this, R.layout.text1, blocosString);
        adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlocos.setAdapter(adapterBusinessType);
        spinnerBlocos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    position--;
                    blocoSelecionado = AddCondominioActivity.this.blocos.get(position);
                    loadUnidades();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinnerUnidades(List<Unidade> unidades) {
        this.unidadeString = new ArrayList<>();
        this.unidades = unidades;
        this.unidadeString.add(getString(R.string.unidades));
        for (Unidade unidade : unidades) {
            unidadeString.add(unidade.getNumero().toString());
        }
        ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(this, R.layout.text1, unidadeString);
        adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidade.setAdapter(adapterBusinessType);
        spinnerUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    position--;
                    unidadeSelecionada = AddCondominioActivity.this.unidades.get(position);
                    radioGroup.setVisibility(View.VISIBLE);
                    btnCadastrarCondominio.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadUnidades() {
        rotaCondominio.getUnidadeBlocos(this, condominioSelecionado.getId(), blocoSelecionado.getId(), this);
    }

    private void loadBlocosCondominios() {
        rotaCondominio.getBlocosCondominio(this, condominioSelecionado.getId(), this);
    }

    @Override
    public void onCondominiosRecebidos(List<Condominio> condominios) {
        progress.setVisibility(View.GONE);
        spinnerCondominios.setVisibility(View.VISIBLE);
        populateSpinnerCondominios(condominios);
    }

    @Override
    public void onGetCondominiosFail(String error) {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onBlocosRecebidos(List<Bloco> blocos) {
        spinnerBlocos.setVisibility(View.VISIBLE);
        populateSpinnerBlocos(blocos);
    }

    @Override
    public void onGetBlocosFail(String error) {

    }

    @Override
    public void onUnidadesRecebidas(List<Unidade> unidades) {
        spinnerUnidade.setVisibility(View.VISIBLE);
        populateSpinnerUnidades(unidades);
    }

    @Override
    public void onGetUnidadesError(String error) {

    }

    @Override
    public void onPerfilRegistrado() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onRegistrationFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void cadastrarUnidade() {
        rotaCondominio.registrarUnidade((Context) this, unidadeSelecionada.getId(), rdProprietario.isChecked(), rdMorador.isChecked(), this);
    }

}
