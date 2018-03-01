package brmobi.moop.ui.messages

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.main.MoopActivity
import brmobi.moop.utils.Scrollable
import kotlinx.android.synthetic.main.fragment_mensagens.*
import javax.inject.Inject

class MensagensFragment : BaseFragment(), Scrollable, LastMessagesMvpView {

    private lateinit var ultimasMensagensAdapter: LastMessagesAdapter

    @Inject
    lateinit var mPresenter: LastMessagesMvpPresenter<LastMessagesMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_mensagens, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun notifyDataSetChanged() {
        ultimasMensagensAdapter.notifyDataSetChanged()
    }

    override fun stopRefresh() {
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }

    override fun showDwellersActivity() {
        (activity as MoopActivity).openDwellersActivity()
    }

    override fun setUp(view: View) {
        refresh.setColorSchemeResources(R.color.colorPrimary)
        refresh.setOnRefreshListener {
            mPresenter.onRefreshListener()
        }
        setupRecyclerView()
        fab.setOnClickListener {
            mPresenter.onFabClick()
        }
        mPresenter.onViewReady()
    }

    private fun setupRecyclerView() {
        recycler_ultimas_mensagens.layoutManager = LinearLayoutManager(context)
        ultimasMensagensAdapter = LastMessagesAdapter(mPresenter)
        recycler_ultimas_mensagens.adapter = ultimasMensagensAdapter
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

    override fun scrollToTop() {

    }

    companion object {

        fun newInstance(): MensagensFragment {
            return MensagensFragment()
        }
    }

    override fun openMessagesActivity(extraUserDestinationIdKey: String, extraUserDestinationIdValue: Long, extraUserDestinationNameKey: String, nameValue: String) {
        val intent = Intent(context, MessagesActivity::class.java)
        intent.putExtra(extraUserDestinationIdKey, extraUserDestinationIdValue)
        intent.putExtra(extraUserDestinationNameKey, nameValue)
        context.startActivity(intent)
    }
}// Required empty public constructor
