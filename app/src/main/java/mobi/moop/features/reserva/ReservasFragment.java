package mobi.moop.features.reserva;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.viewutils.Scrollable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservasFragment extends Fragment implements Scrollable {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        ButterKnife.bind(this, view);
        configureTabLayout();
        return view;
    }

    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_minhas_reservas));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_bens_comuns));

        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView textTab = (TextView) tab.getCustomView().findViewById(R.id.textTab);
                textTab.setTextSize(14);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textTab = (TextView) tab.getCustomView().findViewById(R.id.textTab);
                textTab.setTextSize(12);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Minhas Reservas";
            } else {
                return "Bens Comuns";
            }
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MinhasReservasFragment();
                case 1:
                    return new BensComunsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    /*@BindView(R.id.recyclerBensComuns)
    RecyclerView recyclerBensComuns;

    @BindView(R.id.autorizacaoView)
    View autorizacaoView;

    private List<BemComum> bensComuns = new ArrayList<>();
    private List<Object> reservas = new ArrayList<>();
    private List<Object> listaAdapter = new ArrayList<>();
    private BensComunsAdapter adapter;
    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private Condominio condominioSelecionado;
    private Context context;

    public ReservasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadCondominioSelecionado();
        loadBens();
        loadReservas();
        return view;
    }

    private void loadCondominioSelecionado() {
        this.condominioSelecionado = CondominioRepository.I.getCondominio(context);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void loadReservas() {
        rotaReservas.getReservas(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
    }

    private void loadBens() {
        if (condominioSelecionado.getIsLiberado()) {
            showRecycler();
            rotaReservas.getBensComund(context, CondominioPreferences.I.getLastSelectedCondominio(context), this);
        } else {
            showNaoLiberadoView();
        }
    }

    private void showNaoLiberadoView() {
        recyclerBensComuns.setVisibility(View.GONE);
        autorizacaoView.setVisibility(View.VISIBLE);
    }

    private void showRecycler() {
        recyclerBensComuns.setVisibility(View.VISIBLE);
        autorizacaoView.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        recyclerBensComuns.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BensComunsAdapter(listaAdapter);
        recyclerBensComuns.setAdapter(adapter);
    }

    @Override
    public void onBensComunsRecebidos(List<BemComum> bensComuns) {
        if (bensComuns.size() > 0) {
            this.bensComuns.add(null);
            this.bensComuns.addAll(bensComuns);
            this.listaAdapter.addAll(this.bensComuns);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecebimentoBensComunsErro(String erro) {
        Toast.makeText(context, erro, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReservasRecebidas(List<ReservaBemComum> reservas) {
        if (reservas.size() > 0) {
            this.reservas.add("HEADER");
            this.reservas.addAll(reservas);
            this.listaAdapter.addAll(0, this.reservas);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onRecebimentoReservasError(String errorr) {
        Toast.makeText(context, errorr, Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void scrollToTop() {
    }

}
