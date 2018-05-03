package brmobi.moop.ui.condominium.add


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_add_bloco.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class AddBlocFragment : BaseFragment(), AddBlocMvpView {


    private lateinit var adapter: AddBlocAdapter

    @Inject
    lateinit var mPresenter: AddBlocMvpPresenter<AddBlocMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_add_bloco, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
            mPresenter.handleArguments(arguments)
        }
        return view
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun setFields(condominiumName: String, condominiumZipCode: String, condominiumStreet: String, condominiumNumber: String) {
        textNomeCondominio.text = condominiumName
        textCepCondominio.text = condominiumZipCode
        textEnderecoCondominio.text = condominiumStreet + " - " + condominiumNumber
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        btnAddBloco.setOnClickListener {
            mPresenter.onAddBlocoClick()
        }
        mPresenter.onViewReady()

    }

    private fun setupRecyclerView() {
        recycler_blocos.layoutManager = LinearLayoutManager(context)
        adapter = AddBlocAdapter(mPresenter)
        recycler_blocos!!.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        (context as AddCondominioActivity).setTitutlo(getString(R.string.add_bloco_condominio))
    }


    companion object {
        fun getInstance(condominiumName: String, condominiumZipCode: String, condominiumStreet: String, condominiumNumber: String): AddBlocFragment {
            val bundle = Bundle()
            bundle.putString(AppConstants.CONDOMINIO_NAME_EXTRA_KEY, condominiumName)
            bundle.putString(AppConstants.CONDOMINIO_ZIPCODE_EXTRA_KEY, condominiumZipCode)
            bundle.putString(AppConstants.CONDOMINIO_STREET_EXTRA_KEY, condominiumStreet)
            bundle.putString(AppConstants.CONDOMINIO_NUMBER_EXTRA_KEY, condominiumNumber)
            val fragment = AddBlocFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun showBlocsFragment(condominiumId: Long, condominiumName: String) {
        (activity as AddCondominioActivity).showBlocosFragment(AppConstants.CONDOMINIO_ID_EXTRA_KEY, condominiumId, condominiumName)
    }
}// Required empty public constructor
