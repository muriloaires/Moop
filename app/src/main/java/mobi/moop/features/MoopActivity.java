package mobi.moop.features;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.chamado.ChamadoActivity;
import mobi.moop.features.condominio.AddCondominioActivity;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.features.login.LoginActivity;
import mobi.moop.features.moradores.MoradoresActivity;
import mobi.moop.features.perfil.EditarPerfilActivity;
import mobi.moop.features.reserva.DisponibilidadesActivity;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.repository.UsuarioRepository;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.model.singleton.UsuarioSingleton;

public class MoopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RotaCondominio.CondominiosHandler {

    private static final int ADD_CONDOMINIO = 0;
    private static final int EDIT_PROFILE_RESULT = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager pager;

    ActionBarDrawerToggle toggle;

    RotaCondominioImpl rotaCondominio = new RotaCondominioImpl();
    private List<Condominio> condominios = new ArrayList<>();
    private int lastSelectedIndex;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        configureNavigationDrawer();
        configureMenuCondominios();
        loadCondominios();
    }

    private void configureTabs() {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_feed));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_reservas));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_mensagens));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_notificacoes));
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setOffscreenPageLimit(tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                View root = view.findViewById(R.id.root);
                root.setBackgroundColor(getResources().getColor(R.color.colorTabUnselected));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                View root = view.findViewById(R.id.root);
                root.setBackgroundColor(Color.WHITE);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                try {
                    switch (tab.getPosition()) {
                        case 0:
                            adapter.getFeedFragment().scrollToTop();
                            break;
                        case 1:
                            adapter.getReservasFragment().scrollToTop();
                            break;
                        case 2:
                            adapter.getMensagensFragment().scrollToTop();
                            break;
                        default:
                            adapter.getNotificacoesFragment().scrollToTop();
                    }
                    if (tab.getPosition() == 0) {

                    } else {

                    }
                } catch (Exception e) {

                }

            }
        });
        tabLayout.getTabAt(0).select();
    }

    private void loadCondominios() {
        rotaCondominio.getCondominiosUsuarioLogado(this, this);
    }

    private void configureNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        loadProfileFields();
    }

    private void loadProfileFields() {
        ImageView imgAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        if (!UsuarioRepository.I.getUsuarioLogado(this).getAvatar().equals("")) {
            Picasso.with(this).load(UsuarioSingleton.I.getUsuarioLogado(this).getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
        } else {
            Picasso.with(this).load(R.drawable.placeholder_avatar).into(imgAvatar);
        }
        TextView textNome = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textNome);
        TextView textEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textEmail);
        textNome.setText(UsuarioSingleton.I.getUsuarioLogado(this).getNome());
        textEmail.setText(UsuarioSingleton.I.getUsuarioLogado(this).getUser().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case -1:
                addCondominio();
                break;
            case R.id.editar_perfil:
                showEditarPerfilActivity();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.chamados:
                showChamadosActivity();
                break;
            case R.id.moradores:
                showMoradoresActivity();
                break;

            default:
                SubMenu subMenu = navigationView.getMenu().getItem(0).getSubMenu();
                subMenu.getItem(lastSelectedIndex).setChecked(false);
                selectCondominio(item.getItemId());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChamadosActivity() {
        startActivity(new Intent(this, ChamadoActivity.class));
    }

    private void showMoradoresActivity() {
        startActivity(new Intent(this, MoradoresActivity.class));
    }

    private void showEditarPerfilActivity() {
        startActivityForResult(new Intent(this, EditarPerfilActivity.class), EDIT_PROFILE_RESULT);
    }

    private void selectCondominio(int position) {
        lastSelectedIndex = position;
        Condominio condominio = condominios.get(position);
        CondominioPreferences.I.saveLastSelectedCondominio(this, condominio.getId());
        toolbar.setTitle(condominio.getNome());
        configureTabs();
    }

    private void logout() {
        UsuarioRepository.I.removeUsuarioLogado(this);
        CondominioPreferences.I.deletePreferences(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void addCondominio() {
        startActivityForResult(new Intent(this, AddCondominioActivity.class), ADD_CONDOMINIO);
    }

    private void configureMenuCondominios() {
        Menu menu = navigationView.getMenu();
        menu.clear();
        SubMenu topChannelMenu = menu.addSubMenu(0, Menu.NONE, Menu.NONE, R.string.seus_condominios);
        for (int i = 0; i < condominios.size(); i++) {
            MenuItem menuItem = topChannelMenu.add(0, i, Menu.NONE, condominios.get(i).getNome());
            menuItem.setIcon(condominios.get(i).getIsHorizontal() ? R.drawable.ic_house : R.drawable.ic_predio);
        }
        topChannelMenu.setGroupCheckable(0, true, true);
        menu.add(1, -1, Menu.NONE, "Adicionar Condomínio");
        navigationView.inflateMenu(R.menu.activity_moop_drawer);

        if (condominios.size() > 0) {
            Long lastSelectedCondominio = CondominioPreferences.I.getLastSelectedCondominio(this);
            if (lastSelectedCondominio == -1) {
                selectCondominio(0);
            } else {
                int posicao = 0;
                for (int i = 0; i < condominios.size(); i++) {
                    if (condominios.get(i).getId().equals(lastSelectedCondominio)) {
                        posicao = i;
                        break;
                    }
                }

                topChannelMenu.getItem(posicao).setChecked(true);
                selectCondominio(posicao);
            }
        }

    }

    @Override
    public void onCondominiosRecebidos(List<Condominio> condominios) {
        this.condominios.clear();
        this.condominios.addAll(condominios);
        if (condominios.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.atencao))
                    .setMessage(R.string.nenhum_condominio)
                    .setPositiveButton(R.string.entendi, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addCondominio();
                        }
                    });
            builder.show();
        } else {
            configureMenuCondominios();
            configureTabs();
        }

    }

    @Override
    public void onGetCondominiosFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case ADD_CONDOMINIO:
                if (resultCode == RESULT_OK) {
                    loadCondominios();
                }
                break;
            case EDIT_PROFILE_RESULT:
                if (resultCode == RESULT_OK) {
                    loadProfileFields();
                }
                break;
        }
    }


    public void openDisponibilidadesActivity(BemComum bemComum) {
        Intent intent = new Intent(this, DisponibilidadesActivity.class);
        intent.putExtra("bemId", bemComum.getId());
        intent.putExtra("bemComumNome", bemComum.getNome());
        intent.putExtra("bemComumAvatar", bemComum.getAvatar());
        intent.putExtra("bemComumTermos", bemComum.getTermosDeUso());

        startActivity(intent);
    }
}
