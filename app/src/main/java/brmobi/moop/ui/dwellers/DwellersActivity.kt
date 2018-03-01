package brmobi.moop.ui.dwellers

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.data.db.model.Condominio
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_moradores.*

class DwellersActivity : BaseActivity() {


    private var condominioSelecionado: Condominio? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moradores)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setNavigationIcon(R.drawable.ic_back)
        showMoradoresFragment()
    }

    private fun showMoradoresFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, DwellersListFragment())
        ft.commit()
    }


}
