package brmobi.moop.ui.condominium.add


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.utils.Mask
import kotlinx.android.synthetic.main.fragment_condominios.*
import java.io.Serializable
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class CondominiunsFragment : BaseFragment(), CondominiumMvpView {

    private lateinit var adapter: CondominiosAdapter


    private val watcher = object : TextWatcher {
        internal var isUpdating: Boolean = false
        internal var old = ""
        internal var mask = "##.###-###"

        override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                   count: Int) {
            val str = Mask.unmask(s.toString())
            var mascara = ""
            if (isUpdating) {
                old = str
                isUpdating = false
                return
            }
            var i = 0
            for (m in mask.toCharArray()) {
                if (m != '#' && str.length > old.length) {
                    mascara += m
                    continue
                }
                try {
                    mascara += str[i]
                } catch (e: Exception) {
                    break
                }

                i++
            }
            isUpdating = true
            editCEP.setText(mascara)
            editCEP.setSelection(mascara.length)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(ed: Editable) {
            val s = ed.toString()
            if (s.length == 10) {
                editCEP.removeTextChangedListener(this)
                mPresenter.afterTextChanged(s)
            }
        }
    }


    @Inject
    lateinit var mPresenter: CondominiumMvpPresenter<CondominiumMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_condominios, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }


    override fun setUp(view: View) {
        configureCepMask()
        setupRecyclerView()
        btnAvancar.setOnClickListener {
            mPresenter.onBtnForwardClick()
        }
        btn_nao_encontrei_condominio.setOnClickListener {
            mPresenter.onCondominiumNotFoundClick(editCEP.text.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        (context as AddCondominioActivity).setTitutlo(getString(R.string.escolha_o_condominio))
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupRecyclerView() {
        recyclerCondiminios.layoutManager = LinearLayoutManager(context)
        adapter = CondominiosAdapter(mPresenter)
        recyclerCondiminios.adapter = adapter

    }

    override fun showCondominiumRegisterFragment(registerCondominium: Serializable) {
        (activity as AddCondominioActivity).showRegistroCondominioFragment(registerCondominium)
    }

    private fun configureCepMask() {
        editCEP!!.addTextChangedListener(watcher)
    }


    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showBtnCondominiumNotFound() {
        btn_nao_encontrei_condominio.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        recyclerCondiminios.visibility = View.GONE
    }

    override fun showRecyclerView() {
        recyclerCondiminios.visibility = View.VISIBLE
    }

    override fun hideBtnForward() {
        btnAvancar.visibility = View.INVISIBLE
        btnAvancar.isClickable = false
    }

    override fun showBtnForward() {
        btnAvancar.visibility = View.VISIBLE
        btnAvancar.isClickable = true
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun setTextWatcherZipCode() {
        editCEP.addTextChangedListener(watcher)
    }

    override fun showBlocsFragment(condominiumIdExtraKey: String, condominiumIdExtraValue: Long, condominiumNameExtraKey: String, condominiumNameExtraValue: String) {
        (activity as AddCondominioActivity).showBlocosFragment(condominiumIdExtraKey, condominiumIdExtraValue, condominiumNameExtraValue)
    }


}// Required empty public constructor
