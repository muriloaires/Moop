package brmobi.moop.ui.condominium.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_blocos.*
import javax.inject.Inject

/**
 * Created by murilo aires on 18/08/2017.
 */

class BlocsFragment : BaseFragment(), BlocMvpView {

    private lateinit var adapter: BlocsAdapter

    @Inject
    lateinit var mPresenter: BlocMvpPresenter<BlocMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_blocos, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
            mPresenter.handleArguments(arguments)
        }
        return view
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        mPresenter.onViewReady()
    }

    override fun onResume() {
        super.onResume()
        (context as AddCondominioActivity).setTitutlo(getString(R.string.agora_escolha_bloco))
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    private fun setupRecyclerView() {
        recyclerBlocos.layoutManager = LinearLayoutManager(context)
        adapter = BlocsAdapter(mPresenter)
        recyclerBlocos!!.adapter = adapter
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun showDialogOwnerDweller(mSelectedBloc: Bloco) {
        (activity as AddCondominioActivity).showDialogMoradorProprietario(mSelectedBloc)
    }


}
