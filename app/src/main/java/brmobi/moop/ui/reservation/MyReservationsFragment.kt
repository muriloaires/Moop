package brmobi.moop.ui.reservation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_minhas_reservas.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MyReservationsFragment : BaseFragment(), MyReservationsMvpView {


    @Inject
    lateinit var mPresenter: MyReservationsMvpPresenter<MyReservationsMvpView>

    private lateinit var reservasAdapter: ReservationAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_minhas_reservas, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }

    override fun setUp(view: View) {
        refresh.setOnRefreshListener { mPresenter.onRefreshListener() }
        refresh.setColorSchemeResources(R.color.colorPrimary)
        setupRecyclerView()
        mPresenter.onViewReady()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }
    override fun onPause() {
        super.onPause()
        if (refresh != null) {
            refresh.isRefreshing = false
            refresh.destroyDrawingCache()
            refresh.clearAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupRecyclerView() {
        recycler_reservas.layoutManager = LinearLayoutManager(context)
        reservasAdapter = ReservationAdapter(mPresenter)
        recycler_reservas!!.adapter = reservasAdapter
    }


    override fun showDialogCancelation(position: Int) {
        val builder = AlertDialog.Builder(context).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_cancelar_reserva)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    mPresenter.onConfirmCancelation(position)
                }
                .setNegativeButton(R.string.cancelar) { dialog, which -> }
        builder.show()
    }

    override fun notifyDataSetChanged() {
        reservasAdapter.notifyDataSetChanged()
    }
}// Required empty public constructor
