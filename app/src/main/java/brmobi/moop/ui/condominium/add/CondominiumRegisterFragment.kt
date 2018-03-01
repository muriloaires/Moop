package brmobi.moop.ui.condominium.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.utils.Mask
import kotlinx.android.synthetic.main.fragment_registro_condominio.*
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by murilo aires on 29/09/2017.
 */

class CondominiumRegisterFragment : BaseFragment(), CondominiumRegisterMvpView {


    @Inject
    lateinit var mPresenter: CondominiumRegisterMvpPresenter<CondominiumRegisterMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_registro_condominio, container, false)
        val composite = getActivityComponent()
        if (composite != null) {
            composite.inject(this)
            mPresenter.onAttach(this)
        }
        mPresenter.handleArgument(arguments!!)
        return view
    }

    override fun setUp(view: View) {
        edit_cep.addTextChangedListener(Mask.insert("##.###-###", edit_cep))
        btn_cadastrar_condominio.setOnClickListener {
            mPresenter.onBtnRegisterCondominiumClick(edit_cep.text.toString(), edit_nome_condominio.text.toString(), edit_endereco.text.toString(), edit_numero.text.toString(),
                    edit_telefone.text.toString(), chkHorizontal.isChecked)
        }
        mPresenter.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun showAddBlocFragment(registerCondominium: Serializable) {
        (context as AddCondominioActivity).showAddBlocoFragment(registerCondominium)
    }

    override fun setZipCodeText(zipCode: String) {
        edit_cep.setText(zipCode)
    }

    override fun onResume() {
        super.onResume()
        (context as AddCondominioActivity).setTitutlo(getString(R.string.novo_condominio))
    }

    override fun showZipCodeError(resId: Int) {
        text_input_layout_cep.error = getString(resId)
    }

    override fun showCondominiumNameError(resId: Int) {
        input_layout_nome_condominio.error = getString(resId)
    }

    override fun showCondominiumNumberError(resId: Int) {
        text_input_layout_numero.error = getString(resId)
    }

    override fun showCondominiumAddressError(resId: Int) {
        text_input_layout_endereco.error = getString(resId)
    }

    override fun showCondominiumPhoneNumberError(resId: Int) {
        text_input_layout_telefone.error = getString(resId)
    }


}
