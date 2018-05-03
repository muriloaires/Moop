package brmobi.moop.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import brmobi.moop.ui.base.PagerAdapter
import brmobi.moop.ui.comments.CommentsActivity
import brmobi.moop.ui.condominium.MyCondominiumActivity
import brmobi.moop.ui.condominium.add.AddCondominioActivity
import brmobi.moop.ui.dwellers.ApproveDwellersActivity
import brmobi.moop.ui.dwellers.DwellersActivity
import brmobi.moop.ui.profile.EditProfileActivity
import brmobi.moop.ui.tickets.TicketActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_moop.*
import kotlinx.android.synthetic.main.app_bar_moop.*
import kotlinx.android.synthetic.main.content_moop.*
import javax.inject.Inject


class MoopActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainMvpView {

    companion object {
        const val ADD_CONDOMINIO = 0
        const val EDIT_PROFILE_RESULT = 1
        const val ADD_CONDOMINIUM_MENU_ID = -1
        const val REQUEST_RESERVATION = 2
    }

    lateinit var toggle: ActionBarDrawerToggle

    @Inject
    lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    private var lastSelectedIndex: Int = 0
    private var adapter: PagerAdapter? = null
    private var topChannelMenu: SubMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moop)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        configureNavigationDrawer()
        mPresenter.onNavDrawerSet()
        mPresenter.onViewReady(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mPresenter.onNewIntent(intent)
    }

    override fun selectTab(index: Int) {
        tabLayout?.getTabAt(index)?.select()
    }

    override fun openCommentsActivity(keyPref: String, value: Long) {
        val intent = Intent(this, CommentsActivity::class.java)
        intent.putExtra(keyPref, value)
        startActivity(intent)
    }

    override fun openApproveNewDwellerActivity() {
        startActivity(Intent(this, ApproveDwellersActivity::class.java))
    }

    override fun showNoneCondominiunsRegistered() {
        val builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.atencao))
                .setMessage(R.string.nenhum_condominio)
                .setPositiveButton(R.string.entendi) { dialog, which ->
                    mPresenter.onMenuAddCondominiumClick()
                }
        builder.show()
    }

    override fun setTitle(title: String?) {
        toolbar.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_moop, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.convidar -> mPresenter.onInviteMenuClick()
            R.id.suporte -> mPresenter.onSupportMenuClick()
            R.id.logout -> mPresenter.onLogoutMenuClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun createInviteIntent(stringResId: Int) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(stringResId))
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    override fun showDialogSupport() {
        val view = layoutInflater.inflate(R.layout.dialog_suporte, null)
        val constraint1 = view.findViewById<View>(R.id.constraint_1)
        constraint1.setOnClickListener { openMoopEmail() }
        val constraint2 = view.findViewById<View>(R.id.constraint_2)
        constraint2.setOnClickListener { openMoopSite() }
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.show()
    }

    private fun openMoopEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.email_moop), null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sugestion_subject))

        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)))
    }

    private fun openMoopSite() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_moop)))
        startActivity(browserIntent)
    }

    override fun configureTabs() {
        tabLayout!!.removeAllTabs()
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.tab_feed))
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.tab_reservas))
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.tab_mensagens))
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.tab_notificacoes))
        adapter = PagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.offscreenPageLimit = tabLayout!!.tabCount
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                val view = tab.customView
                val root = view!!.findViewById<View>(R.id.root)
                root.setBackgroundColor(resources.getColor(R.color.colorTabUnselected))

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val view = tab.customView
                val root = view!!.findViewById<View>(R.id.root)
                root.setBackgroundColor(Color.WHITE)

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                try {
                    when (tab.position) {
                        0 -> adapter!!.feedFragment.scrollToTop()
                        1 -> adapter!!.reservasFragment.scrollToTop()
                        2 -> adapter!!.mensagensFragment.scrollToTop()
                        else -> adapter!!.notificacoesFragment.scrollToTop()
                    }
                    if (tab.position == 0) {

                    } else {

                    }
                } catch (e: Exception) {

                }

            }
        })
        tabLayout!!.getTabAt(0)!!.select()
    }

    private fun configureNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()
        nav_view!!.setNavigationItemSelectedListener(this)
        nav_view.getHeaderView(0).findViewById<ImageView>(R.id.imgEdit).setOnClickListener {
            mPresenter.onEditProfileClick()
        }
    }

    override fun setUsername(username: String) {
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.textNome).text = username
    }

    override fun setEmail(userEmail: String) {
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.textEmail).text = userEmail
    }

    override fun setAvatarPlaceholder(drawableResId: Int) {
        Picasso.with(this).load(drawableResId).into(nav_view.getHeaderView(0).findViewById<ImageView>(R.id.imgAvatar))
    }

    override fun loadProfilePic(userProfilePic: String) {
        Picasso.with(this).load(userProfilePic).placeholder(R.drawable.placeholder_avatar).into(nav_view.getHeaderView(0).findViewById<ImageView>(R.id.imgAvatar))
    }

    override fun openChamadosActivity() {
        startActivity(Intent(this, TicketActivity::class.java))
    }

    override fun openDwellersActivity() {
        startActivity(Intent(this, DwellersActivity::class.java))
    }

    override fun openMyCondominiumActivity() {
        startActivity(Intent(this, MyCondominiumActivity::class.java))
    }

    override fun showEditProfileActivity() {
        startActivityForResult(Intent(this, EditProfileActivity::class.java), EDIT_PROFILE_RESULT)
    }

    override fun openAddCondominiumActivity() {
        startActivityForResult(Intent(this, AddCondominioActivity::class.java), ADD_CONDOMINIO)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            ADD_CONDOMINIUM_MENU_ID -> mPresenter.onMenuAddCondominiumClick()
            R.id.chamados -> mPresenter.onMenuChamadosClick()
            R.id.moradores -> mPresenter.onMenuDwellersClick()
            R.id.aprovar_moradores -> mPresenter.onApproveDwellersClick()
            R.id.meu_condominio -> mPresenter.onMyCondominiumClick()
            R.id.gerenciar_condominio -> mPresenter.onManageCondominiumClick()
            R.id.desvincular -> mPresenter.onDetachCondominiumClick()
            else -> {
                val subMenu = nav_view!!.menu.getItem(0).subMenu
                subMenu.getItem(lastSelectedIndex).isChecked = false
                mPresenter.onCondominiumSelected(item.itemId)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun clearNavMenu() {
        val menu = nav_view!!.menu
        menu.clear()
    }

    override fun createTopChannelMenu() {
        val menu = nav_view!!.menu
        topChannelMenu = menu.addSubMenu(0, Menu.NONE, Menu.NONE, R.string.seus_condominios)
    }

    override fun addNewCondominiumMenu(stringResId: Int) {
        nav_view!!.menu.add(1, ADD_CONDOMINIUM_MENU_ID, Menu.NONE, getString(stringResId))
    }

    override fun addMenuItemToTopChannelMenu(nome: String?, position: Int, drawableResId: Int) {
        val menuItem = topChannelMenu?.add(0, position, Menu.NONE, nome)
        menuItem?.setIcon(drawableResId)
    }

    override fun setTopChannelMenuCheckable(position: Int) {
        topChannelMenu?.setGroupCheckable(0, true, true)
        topChannelMenu?.getItem(position)?.isChecked = true
    }

    override fun inflateMenuSindico(menuResId: Int) {
        nav_view!!.inflateMenu(menuResId)
    }

    override fun addNavOptionsMenu(menuResId: Int) {
        nav_view!!.inflateMenu(menuResId)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_CONDOMINIO -> if (resultCode == Activity.RESULT_OK) {
                mPresenter.onCondominiumAdded()
            }
            EDIT_PROFILE_RESULT -> if (resultCode == Activity.RESULT_OK) {
                mPresenter.onProfileEdited()
            }
        }
    }


}
