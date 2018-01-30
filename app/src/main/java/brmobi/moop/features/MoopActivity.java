package brmobi.moop.features;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import brmobi.moop.R;
import brmobi.moop.features.chamado.ChamadoActivity;
import brmobi.moop.features.comentarios.ComentariosActivity;
import brmobi.moop.features.condominio.AddCondominioActivity;
import brmobi.moop.features.condominio.CondominioPreferences;
import brmobi.moop.features.detalhe_condominio.MeuCondominioActivity;
import brmobi.moop.features.login.LoginActivity;
import brmobi.moop.features.moradores.AprovarMoradoresActivity;
import brmobi.moop.features.moradores.MoradoresActivity;
import brmobi.moop.features.perfil.EditarPerfilActivity;
import brmobi.moop.features.reserva.DisponibilidadesActivity;
import brmobi.moop.model.entities.BemComum;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.repository.CondominioRepository;
import brmobi.moop.model.repository.UsuarioRepository;
import brmobi.moop.model.rotas.RotaCondominio;
import brmobi.moop.model.rotas.impl.RotaCondominioImpl;
import brmobi.moop.model.singleton.UsuarioSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RotaCondominio.CondominiosHandler {

    private static final int ADD_CONDOMINIO = 0;
    private static final int EDIT_PROFILE_RESULT = 1;
    public static final int REQUEST_RESERVA = 2;
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
    private boolean hasAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        configureNavigationDrawer();
        loadCondominios();
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getStringExtra("action");
        if (action != null) {
            switch (action) {
                case "mensagens":
                    Long condominioId = Long.parseLong(intent.getStringExtra("condominio_id"));
                    //verifica se os condominios ja foram carregados
                    if (condominios.size() > 0) {
                        if (CondominioRepository.I.getCondominio(this).getId().equals(condominioId)) {
                            tabLayout.getTabAt(2).select();
                            if (adapter.getMensagensFragment() != null) {
                                adapter.getMensagensFragment().loadUltimasMensagens();
                            }
                        } else {
                            int position = -1;
                            for (int i = 0; i < condominios.size(); i++) {
                                Condominio condominio = condominios.get(i);
                                if (condominio.getId().equals(condominioId)) {
                                    position = i;
                                    break;
                                }
                            }
                            if (position != -1) {
                                selectCondominio(position);
                                tabLayout.getTabAt(2).select();
                            }
                        }
                    } else {
                        hasAction = true;
                    }
                    break;
                case "comentarios":
                    Intent intent2 = new Intent(this, ComentariosActivity.class);
                    intent2.putExtra("feedId", Long.parseLong(getIntent().getStringExtra("feed_id")));
                    startActivity(intent2);
                    break;
                case "novo_morador":
                    Long condominioID = Long.parseLong(intent.getStringExtra("condominio_id"));
                    //verifica se os condominios ja foram carregados
                    if (condominios.size() > 0) {
                        if (CondominioRepository.I.getCondominio(this).getId().equals(condominioID)) {
                            showAprovarMoradoresActivity();
                        } else {
                            int position = -1;
                            for (int i = 0; i < condominios.size(); i++) {
                                Condominio condominio = condominios.get(i);
                                if (condominio.getId().equals(condominioID)) {
                                    position = i;
                                    break;
                                }
                            }
                            if (position != -1) {
                                selectCondominio(position);
                                showAprovarMoradoresActivity();
                            }
                        }
                    } else {
                        hasAction = true;
                    }
                    loadCondominios();
                    break;
                case "novo_morador_liberado":
                    loadCondominios();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_moop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.convidar:

                break;
            case R.id.suporte:
                showDialogSuporte();
                break;

            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogSuporte() {
        View view = getLayoutInflater().inflate(R.layout.dialog_suporte, null);
        View constraint1 = view.findViewById(R.id.constraint_1);
        constraint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoopEmail();
            }
        });
        View constraint2 = view.findViewById(R.id.constraint_2);
        constraint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoopSite();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.show();
    }

    private void openMoopEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.email_moop), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sugestion_subject));

        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)));
    }

    private void openMoopSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_moop)));
        startActivity(browserIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        rotaCondominio.cancelGetCondominiosUsuarioRequisition();
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
        ImageView imgEditarPerfil = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgEdit);
        imgEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditarPerfilActivity();
            }
        });
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
            case R.id.chamados:
                showChamadosActivity();
                break;
            case R.id.moradores:
                showMoradoresActivity();
                break;
            case R.id.aprovar_moradores:
                showAprovarMoradoresActivity();
                break;
            case R.id.meu_condominio:
                showMeuCondominioActivity();
                break;
            case R.id.gerenciar_condominio:
                showMeuCondominioActivity();
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

    private void showMeuCondominioActivity() {
        startActivity(new Intent(this, MeuCondominioActivity.class));
    }

    private void showAprovarMoradoresActivity() {
        startActivity(new Intent(this, AprovarMoradoresActivity.class));
    }

    private void showChamadosActivity() {
        startActivity(new Intent(this, ChamadoActivity.class));
    }

    public void showMoradoresActivity() {
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
        configureMenuCondominios(position);
        configureTabs();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        UsuarioRepository.I.removeUsuarioLogado(this);
        CondominioPreferences.I.deletePreferences(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void addCondominio() {
        startActivityForResult(new Intent(this, AddCondominioActivity.class), ADD_CONDOMINIO);
    }

    private void configureMenuCondominios(int position) {
        Menu menu = navigationView.getMenu();
        menu.clear();
        SubMenu topChannelMenu = menu.addSubMenu(0, Menu.NONE, Menu.NONE, R.string.seus_condominios);
        for (int i = 0; i < condominios.size(); i++) {
            MenuItem menuItem = topChannelMenu.add(0, i, Menu.NONE, condominios.get(i).getNome());
            menuItem.setIcon(condominios.get(i).getIsHorizontal() ? R.drawable.ic_house : R.drawable.ic_predio);
        }
        topChannelMenu.setGroupCheckable(0, true, true);
        topChannelMenu.getItem(position).setChecked(true);
        if (CondominioRepository.I.getCondominio(this).getIsSindico()) {
            navigationView.inflateMenu(R.menu.activity_moop_drawer_sindico);
        }
        menu.add(1, -1, Menu.NONE, "Adicionar Condomínio");
        navigationView.inflateMenu(R.menu.activity_moop_drawer);

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


                selectCondominio(posicao);
            }
            if (hasAction) {
                handleIntent(getIntent());
                hasAction = false;
            }
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
        startActivityForResult(intent, REQUEST_RESERVA);
    }
}
