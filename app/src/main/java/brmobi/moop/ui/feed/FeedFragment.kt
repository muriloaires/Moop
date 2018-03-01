package brmobi.moop.ui.feed


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.comments.CommentsActivity
import brmobi.moop.ui.publication.ImageActivity
import brmobi.moop.ui.publication.NewPostActivity
import brmobi.moop.utils.EndlessRecyclerOnScrollListener
import brmobi.moop.utils.Scrollable
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : BaseFragment(), Scrollable, FeedMvpView {


    private lateinit var feedAdapter: FeedAdapter
    private lateinit var scrollListener: EndlessRecyclerOnScrollListener

    @Inject
    lateinit var mPresenter: FeedMvpPresenter<FeedMvpView>


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_feed, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        refresh.setOnRefreshListener({
            mPresenter.onRefreshUpdated()
        })
        refresh.setColorSchemeResources(R.color.colorPrimary)
        fab_createevent.setOnClickListener {
            mPresenter.onFabCreatePostClick()
        }
        mPresenter.onViewReady()
    }

    override fun openNewPostActivity() {
        startActivityForResult(Intent(context, NewPostActivity::class.java), WRITE_POST)
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

    override fun showLockedCondominium() {
        recyclerFeed.visibility = View.GONE
        nenhumaPublicacao.visibility = View.GONE
        autorizacaoView.visibility = View.VISIBLE
    }

    override fun resetScrollListenerState() {
        scrollListener.resetState()
    }

    override fun showRecycler() {
        recyclerFeed.visibility = View.VISIBLE
        autorizacaoView.visibility = View.GONE
        nenhumaPublicacao.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(context)
        recyclerFeed.layoutManager = manager
        feedAdapter = FeedAdapter(mPresenter)
        recyclerFeed.adapter = feedAdapter
        recyclerFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                if (dy > 0 && fab_createevent.isShown) {
                    fab_createevent.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                        override fun onHidden(fab: FloatingActionButton?) {
                            fab!!.visibility = View.GONE
                        }
                    })
                } else if (dy < 0) {
                    fab_createevent.show()
                }
            }


        })
        scrollListener = object : EndlessRecyclerOnScrollListener(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPresenter.onLoadMore()
            }
        }
    }

    override fun showNoPublications() {
        nenhumaPublicacao.visibility = View.VISIBLE
    }

    override fun notifyDataSetChanged() {
        feedAdapter.notifyDataSetChanged()
    }

    override fun removeScrollListener() {
        recyclerFeed.removeOnScrollListener(scrollListener)
    }

    override fun stopRefreshing() {
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            WRITE_POST -> if (resultCode == Activity.RESULT_OK) {
                mPresenter.onNewPostActivitySuccessResult()
            }
        }
    }

    override fun scrollToTop() {
        try {
            recyclerFeed.smoothScrollToPosition(0)
        } catch (e: NullPointerException) {

        }

    }


    override fun showFeedItemOptions() {
        val builder = AlertDialog.Builder(getContext()).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_apagar_postagem)
                .setPositiveButton(R.string.sim) { _, _ ->
                    mPresenter.onDeleteItemFeedClick()
                }.setNegativeButton(R.string.cancelar) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    override fun showCommentsActivity(feedIdExtraKey: String, feedIdExtraValue: Long) {
        val intent = Intent(context, CommentsActivity::class.java)
        intent.putExtra(feedIdExtraKey, feedIdExtraValue)
        context.startActivity(intent)
    }

    override fun showImagePostActivity(imgPostExtraKey: String, url: String) {
        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra(imgPostExtraKey, url)
        context.startActivity(intent)
    }

    companion object {
        const val WRITE_POST = 1
    }
}// Required empty public constructor
