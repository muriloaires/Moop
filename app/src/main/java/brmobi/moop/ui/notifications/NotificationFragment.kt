package brmobi.moop.ui.notifications


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.comments.CommentsActivity
import brmobi.moop.utils.Scrollable
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.dwellers.ApproveDwellersActivity
import kotlinx.android.synthetic.main.fragment_notificacoes.*
import javax.inject.Inject

class NotificationFragment : BaseFragment(), Scrollable, NotificationMvpView {

    private lateinit var adapter: NotificationAdapter

    @Inject
    lateinit var mPresenter: NotificationMvpPresenter<NotificationMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_notificacoes, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        swipe.setOnRefreshListener {
            mPresenter.onRefresh()
        }
        mPresenter.onViewReady()
    }

    override fun showCommentsActivity(extraKey: String, extraValue: Long) {
        val intent = Intent(activity, CommentsActivity::class.java)
        intent.putExtra(extraKey, extraValue)
        startActivity(intent)
    }

    override fun showApproveDwellersActivity() {
        startActivity(Intent(activity, ApproveDwellersActivity::class.java))
    }

    override fun stopRefreshing() {
        if (swipe.isRefreshing) {
            swipe.isRefreshing = false
        }
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recycler_notificacoes.layoutManager = LinearLayoutManager(context)
        adapter = NotificationAdapter(mPresenter)
        recycler_notificacoes.adapter = adapter
    }

    override fun scrollToTop() {

    }


    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }
}// Required empty public constructor
