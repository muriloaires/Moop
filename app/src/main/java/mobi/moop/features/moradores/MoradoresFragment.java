package mobi.moop.features.moradores;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RotaMoradores;
import mobi.moop.model.rotas.impl.RotaMoradoresImpl;
import mobi.moop.model.singleton.UsuarioSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoradoresFragment extends Fragment implements RotaMoradores.MoradoresHandler, SearchView.OnQueryTextListener {
    @BindView(R.id.recyclerMoradores)
    RecyclerView recyclerMoradores;

    private List<Usuario> moradores = new ArrayList<>();
    private RotaMoradoresImpl rotaMoradores = new RotaMoradoresImpl();
    private MoradoresAdapter adapterMoradores;
    private Condominio condominioSelecionado;

    public MoradoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moradores, container, false);
        ButterKnife.bind(this, view);
        this.condominioSelecionado = CondominioRepository.I.getCondominio(getContext());
        setupRecyclerView();
        loadMoredores("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        rotaMoradores.cancelGetMoradoresRequisition();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_moradores, menu);
        SearchManager searchManager =
                (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getString(R.string.nome_morador));
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        loadMoredores("");
                        return true;
                    }
                });
    }

    private void loadMoredores(String query) {
        rotaMoradores.getMoradores(UsuarioSingleton.I.getUsuarioLogado(getContext()), condominioSelecionado, getContext(), this, query);
    }

    private void setupRecyclerView() {
        recyclerMoradores.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMoradores = new MoradoresAdapter(moradores);
        recyclerMoradores.setAdapter(adapterMoradores);
    }

    @Override
    public void onMoradoresRecebidos(List<Usuario> moradores) {
        this.moradores.clear();
        this.moradores.addAll(moradores);
        adapterMoradores.notifyDataSetChanged();
    }

    @Override
    public void onRecebimentoMoradoresFail(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadMoredores(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
