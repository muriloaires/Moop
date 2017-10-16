package mobi.moop.features.reserva;


import android.app.Activity;
import android.content.Intent;
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
import mobi.moop.features.MoopActivity;
import mobi.moop.features.feed.FeedFragment;
import mobi.moop.features.mensagens.MensagensFragment;
import mobi.moop.features.notification.NotificacoesFragment;
import mobi.moop.features.viewutils.Scrollable;
import mobi.moop.model.entities.BemComum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservasFragment extends Fragment implements Scrollable {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        ButterKnife.bind(this, view);
        configureTabLayout();
        return view;
    }

    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_bens_comuns));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_minhas_reservas));
        adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(adapter);
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
        private final ReservasFragment fragment;
        int mNumOfTabs;
        private MinhasReservasFragment minhasReservasFragment;
        private BensComunsFragment bensComunsFragment;

        public PagerAdapter(FragmentManager fm, int NumOfTabs, ReservasFragment fragment) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.fragment = fragment;
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
                    BensComunsFragment fragment = new BensComunsFragment();
                    fragment.setPagerAdapter(this);
                    return fragment;
                case 1:
                    MinhasReservasFragment fragment1 = new MinhasReservasFragment();
                    fragment1.setPagerAdater(this);
                    return fragment1;
                default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    bensComunsFragment = (BensComunsFragment) createdFragment;
                    break;
                default:
                    minhasReservasFragment = (MinhasReservasFragment) createdFragment;

            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        public MinhasReservasFragment getMinhasReservasFragment() {
            return minhasReservasFragment;
        }

        public BensComunsFragment getBensComunsFragment() {
            return bensComunsFragment;
        }

        public void openDispobinilidadeActivity(BemComum bemComum) {
            fragment.openDisponilidadeActivity(bemComum);
        }
    }

    private void openDisponilidadeActivity(BemComum bemComum) {
        Intent intent = new Intent(getContext(), DisponibilidadesActivity.class);
        intent.putExtra("bemId", bemComum.getId());
        intent.putExtra("bemComumNome", bemComum.getNome());
        intent.putExtra("bemComumAvatar", bemComum.getAvatar());
        intent.putExtra("bemComumTermos", bemComum.getTermosDeUso());
        startActivityForResult(intent, MoopActivity.REQUEST_RESERVA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MoopActivity.REQUEST_RESERVA && resultCode == Activity.RESULT_OK) {
            adapter.getMinhasReservasFragment().loadReservas();
        }
    }

    @Override
    public void scrollToTop() {
    }

}
