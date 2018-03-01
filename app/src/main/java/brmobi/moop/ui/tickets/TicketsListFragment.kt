package brmobi.moop.ui.tickets


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_chamado.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TicketsListFragment : BaseFragment(), TicketListMvpView {

    @Inject
    lateinit var mPresenter: TicketListMvpPresenter<TicketListMvpView>
    private lateinit var chamadosAdapter: TicketsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_chamado, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        ButterKnife.bind(this, view)

        return view
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        btn_criar_chamado.setOnClickListener {
            mPresenter.onBtnCriarChamadoClick()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResumeCalled()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupRecyclerView() {
        recycler_chamados.layoutManager = LinearLayoutManager(context)
        chamadosAdapter = TicketsAdapter(mPresenter)
        recycler_chamados.adapter = chamadosAdapter
    }

    override fun showNewTicketFragment() {
        (activity as TicketActivity).showCriarChamadoFragment()
    }

    override fun notifyDataSetChanged() {
        chamadosAdapter.notifyDataSetChanged()
    }

    override fun showNoTicketsView() {
        nenhumChamado.visibility = View.VISIBLE
        recycler_chamados.visibility = View.GONE
    }

    override fun showTicketsView() {
        nenhumChamado.visibility = View.GONE
        recycler_chamados.visibility = View.VISIBLE
    }

}// Required empty public constructor
