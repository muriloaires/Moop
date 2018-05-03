package brmobi.moop.ui.dwellers


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.ui.messages.MessagesActivity
import brmobi.moop.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_moradores.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class DwellersListFragment : BaseFragment(), SearchView.OnQueryTextListener, DwellersMvpView {

    @Inject
    lateinit var mPresenter: DwellersMvpPresenter<DwellersMvpView>

    private lateinit var dwellersAdapter: DwellersAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_moradores, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun notifyDataSetChanged() {
        dwellersAdapter.notifyDataSetChanged()
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        setHasOptionsMenu(true)
        mPresenter.onViewReady()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_moradores, menu)
        val searchManager = context.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(activity.componentName))
        searchView.queryHint = getString(R.string.nome_morador)
        val linearLayout1 = searchView.getChildAt(0) as LinearLayout
        val linearLayout2 = linearLayout1.getChildAt(2) as LinearLayout
        val linearLayout3 = linearLayout2.getChildAt(1) as LinearLayout
        val autoComplete = linearLayout3.getChildAt(0) as AutoCompleteTextView
        autoComplete.textSize = 14f

        searchView.setOnQueryTextListener(this)
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search),
                object : MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                        mPresenter.onMenuItemActionCollapse()
                        return true
                    }
                })
    }


    private fun setupRecyclerView() {
        recyclerMoradores.layoutManager = LinearLayoutManager(context)
        dwellersAdapter = DwellersAdapter(mPresenter)
        recyclerMoradores.adapter = dwellersAdapter
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        mPresenter.onTextQuerySubmit(query)
        return false
    }

    override fun openMessageActivity(dwellerId: Long, nome: String) {
        val intent = Intent(activity, MessagesActivity::class.java)
        intent.putExtra(AppConstants.EXTRA_USER_DESTINATION_ID, dwellerId)
        intent.putExtra(AppConstants.EXTRA_USER_DESTINATION_NAME, nome)
        startActivity(intent)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        mPresenter.onQueryTextChange(newText)
        return false
    }
}// Required empty public constructor
