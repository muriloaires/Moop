package mobi.moop.features;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.condominio.AddCondominioActivity;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.impl.RotaCondominioImpl;
import mobi.moop.model.singleton.UsuarioSingleton;

public class MoopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RotaCondominio.CondominiosHandler {

    private static final int ADD_CONDOMINIO = 0;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        configureNavigationDrawer();
        configureTabs();
        loadCondominios();
    }

    private void configureTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.timeline).setIcon(R.drawable.ic_feed));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.reservas).setIcon(R.drawable.ic_calendar));
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorTabUnselected), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        ImageView imgAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        Picasso.with(this).load(UsuarioSingleton.I.getUsuarioLogado(this).getAvatar()).placeholder(R.drawable.placeholder_avatar).into(imgAvatar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.moop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case -1:
                addCondominio();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addCondominio() {
        startActivityForResult(new Intent(this, AddCondominioActivity.class), ADD_CONDOMINIO);
    }

    private void configureMenuCondominios(List<Condominio> condominios) {
        Menu menu = navigationView.getMenu();
        SubMenu topChannelMenu = menu.addSubMenu(0, Menu.NONE, Menu.NONE, R.string.seus_condominios);
        Long lastSelectedCondominio = CondominioPreferences.I.getLastSelectedCondominio(this);
        for (int i = 0; i < condominios.size(); i++) {
            MenuItem menuItem = topChannelMenu.add(0, i, Menu.NONE, condominios.get(i).getNome());
            menuItem.setIcon(condominios.get(i).getIsHorizontal() ? R.drawable.ic_house : R.drawable.ic_predio);
            if (!lastSelectedCondominio.equals(-1L) && lastSelectedCondominio.equals(condominios.get(i).getId())) {
                menuItem.setChecked(true);
            } else if (lastSelectedCondominio.equals(-1L) && i == 0) {
                menuItem.setChecked(true);
                CondominioPreferences.I.saveLastSelectedCondominio(this, condominios.get(i).getId());
            }
        }
        topChannelMenu.setGroupCheckable(0, true, true);
        menu.add(1, -1, Menu.NONE, "Adicionar Condomínio");
        navigationView.inflateMenu(R.menu.activity_moop_drawer);
    }

    @Override
    public void onCondominiosRecebidos(List<Condominio> condominios) {
        configureMenuCondominios(condominios);
    }

    @Override
    public void onGetCondominiosFail(String error) {

    }
}
