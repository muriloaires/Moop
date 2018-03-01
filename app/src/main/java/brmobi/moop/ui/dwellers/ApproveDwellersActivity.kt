package brmobi.moop.ui.dwellers

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_aprovar_moradores.*
import javax.inject.Inject

class ApproveDwellersActivity : BaseActivity(), ApproveDwellersMvpView {

    @Inject
    lateinit var mPresenter: ApproveDwellersMvpPresenter<ApproveDwellersMvpView>

    private lateinit var adapter: ApproveDwellersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aprovar_moradores)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        setupRecyclerView()
        mPresenter.onViewReady()

    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupRecyclerView() {
        recyclerMoradores!!.layoutManager = LinearLayoutManager(this)
        adapter = ApproveDwellersAdapter(mPresenter)
        recyclerMoradores!!.adapter = adapter
    }

    override fun showDialogApprove(position: Int) {
        val perfilHabitacional = mPresenter.getDwellers()[position]
        val desejaAprovar = "Deseja aceitar o(a) morador(a) " + perfilHabitacional.perfil.nome + " na unidade " + perfilHabitacional.unidadeHabitacional.numero!!.toString() + "?"
        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(desejaAprovar)
                .setPositiveButton(R.string.aceitar) { dialog, which ->
                    mPresenter.onDialogApproveClick(position)
                }
                .setNegativeButton(R.string.cancelar) { dialog, which -> }
        builder.show()
    }

    override fun showDialogDesaprove(position: Int) {
        val perfilHabitacional = mPresenter.getDwellers()[position]
        val desejaAprovar = "Deseja rejeitar o(a) morador(a) " + perfilHabitacional.perfil.nome + " na unidade " + perfilHabitacional.unidadeHabitacional.numero!!.toString() + "?"
        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(desejaAprovar)
                .setPositiveButton(R.string.aceitar) { dialog, which ->
                    mPresenter.onDialogDesaproveClick(position)
                }
                .setNegativeButton(R.string.cancelar) { dialog, which -> }
        builder.show()
    }

}
