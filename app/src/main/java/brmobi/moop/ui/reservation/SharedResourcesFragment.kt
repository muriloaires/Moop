package brmobi.moop.ui.reservation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.main.MoopActivity
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_bens_comuns.*
import java.io.Serializable
import javax.inject.Inject

class SharedResourcesFragment : BaseFragment(), SharedResourcesMvpView {


    @Inject
    lateinit var mPresenter: SharedResourcesMvpPresenter<SharedResourcesMvpView>
    lateinit var mAdapter: SharedResourcesAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_bens_comuns, container, false)
        ButterKnife.bind(this, view)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        refresh.setColorSchemeResources(R.color.colorPrimary)
        refresh.setOnRefreshListener {
            mPresenter.onRefreshListener()
        }
        mPresenter.onViewReady()
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
        recyclerBensComuns!!.layoutManager = LinearLayoutManager(context)
        mAdapter = SharedResourcesAdapter(mPresenter)
        recyclerBensComuns!!.adapter = mAdapter
    }

    override fun openAvailableActivity(sharedResourceExtraKey: String, serializable: Serializable) {
        val intent = Intent(activity, DisponibilityActivity::class.java)
        intent.putExtra(sharedResourceExtraKey, serializable)

        startActivityForResult(intent, MoopActivity.REQUEST_RESERVATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.onSharedResourceReserved()
        }
    }

    override fun selectMyReservationsFragment() {

    }

    override fun showAutorizationView() {
        autorizacaoView.visibility = View.VISIBLE
        recyclerBensComuns.visibility = View.GONE
    }

    override fun showRecyclerView() {
        autorizacaoView.visibility = View.GONE
        recyclerBensComuns.visibility = View.VISIBLE
    }

    override fun notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged()
    }

    override fun stopRefreshing() {
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }


}// Required empty public constructor
