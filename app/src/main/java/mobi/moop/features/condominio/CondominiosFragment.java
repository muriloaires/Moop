package mobi.moop.features.condominio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.utils.Mask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CondominiosFragment extends Fragment implements RotaCondominio.CondominiosHandler {

    @BindView(R.id.editCEP)
    EditText editCep;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.btnAvancar)
    Button btnCadastrarCondominio;


    @OnClick(R.id.btnAvancar)
    public void btnAvancarAction(View view) {
        loadBlocos();
    }

    @OnClick(R.id.btn_nao_encontrei_condominio)
    public void btnNaoEncontreiAction(View view) {
        showRegistroCondominio();
    }

    @BindView(R.id.recyclerCondiminios)
    RecyclerView recyclerCondominios;

    @BindView(R.id.viewNenhumCondominioEncontrado)
    View viewNenhumCondominioEncontrado;

    private List<Condominio> condominios = new ArrayList<>();
    private CondominiosAdapter adapter;

    private RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private int lastSelected;
    private TextWatcher watcher = new TextWatcher() {
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
                editCep.removeTextChangedListener(this);
                getCondominiosCep(s);
            }
        }
    };

    public CondominiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_condominios, container, false);
        ButterKnife.bind(this, view);
        configureCepMask();
        setupRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AddCondominioActivity) getContext()).setTitutlo(getString(R.string.escolha_o_condominio));
    }

    private void setupRecyclerView() {
        recyclerCondominios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CondominiosAdapter(condominios, this);
        recyclerCondominios.setAdapter(adapter);

    }

    private void showRegistroCondominio() {
        ((AddCondominioActivity) getContext()).showRegistroCondominioFragment();
    }

    private void configureCepMask() {
        editCep.addTextChangedListener(watcher);
    }

    private void getCondominiosCep(String cep) {
        progress.setVisibility(View.VISIBLE);
        rotaCondominio.getCondominiosCep(getContext(), cep, this);
    }

    @Override
    public void onCondominiosRecebidos(List<Condominio> condominios) {
        progress.setVisibility(View.GONE);
        editCep.addTextChangedListener(watcher);
        this.condominios.clear();
        this.condominios.addAll(condominios);
        adapter.notifyDataSetChanged();
        if (condominios.size() == 0) {
            Toast.makeText(getContext(), getString(R.string.nenhum_condominio_encontrado), Toast.LENGTH_SHORT).show();
            viewNenhumCondominioEncontrado.setVisibility(View.VISIBLE);
            recyclerCondominios.setVisibility(View.GONE);
            btnCadastrarCondominio.setVisibility(View.GONE);
        } else {
            viewNenhumCondominioEncontrado.setVisibility(View.GONE);
            recyclerCondominios.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetCondominiosFail(String error) {
        progress.setVisibility(View.GONE);
        editCep.addTextChangedListener(watcher);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void select(int adapterPosition) {
        btnCadastrarCondominio.setVisibility(View.VISIBLE);
        if (lastSelected != -1) {
            condominios.get(lastSelected).setSelected(false);
        }
        condominios.get(adapterPosition).setSelected(true);
        lastSelected = adapterPosition;
        adapter.notifyDataSetChanged();
    }

    private void loadBlocos() {
        if (lastSelected == -1) {
            Toast.makeText(getContext(), getString(R.string.escolha_um_condominio), Toast.LENGTH_SHORT).show();
        } else {
            ((AddCondominioActivity) getContext()).showBlocosFragment(condominios.get(lastSelected));
        }
    }

}
