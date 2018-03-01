package brmobi.moop.ui.tickets

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_chamado.*

class TicketActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chamado)
        ButterKnife.bind(this)
        configureToolbar()
        showChamadosFragment()
    }

    private fun showChamadosFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, TicketsListFragment())
        ft.commit()

    }

    private fun configureToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun showCriarChamadoFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, NewTicketFragment())
        ft.addToBackStack("main")
        ft.commit()
    }
}
