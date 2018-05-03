package brmobi.moop.ui.reservation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import brmobi.moop.utils.AppConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_descricao_bem_comum.*
import java.io.Serializable
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SharedResourceDescriptionFragment : BaseFragment(), SharedResourceDescriptionMvpView {


    @Inject
    lateinit var mPresenter: SharedResourveDescriptionMvpPresenter<SharedResourceDescriptionMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_descricao_bem_comum, container, false)
        val composite = getActivityComponent()
        if (composite != null) {
            composite.inject(this)
            mPresenter.onAttach(this)
        }
        mPresenter.handleArguments(arguments)

        return view

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun setUp(view: View) {
        btn_eu_concordo.setOnClickListener {
            mPresenter.onBtnIAgreeClick()
        }
        mPresenter.onViewReady()
    }

    override fun showCalendarFragment(extraKey: String, extraSharedResource: Serializable) {
        (activity as DisponibilityActivity).showCalendarioFragment(extraKey, extraSharedResource)
    }

    override fun setSharedResourceName(nome: String) {
        bem_comum_nome.text = nome
    }

    override fun showSharedResourceTerms(termosDeUso: String) {
        termos_bem_comum.text = termosDeUso
    }

    override fun showSharedResourceImage(avatar: String?) {
        if (!avatar.isNullOrEmpty()) {
            Picasso.with(context).load(AppConstants.BASE_URL + avatar).into(img_bem_comum)
        }
    }

    companion object {

        fun getInstance(extraKey: String, extraSharedResource: Serializable): SharedResourceDescriptionFragment {
            val fragment = SharedResourceDescriptionFragment()
            val bundle = Bundle()
            bundle.putSerializable(extraKey, extraSharedResource)
            fragment.arguments = bundle
            return fragment
        }
    }
}// Required empty public constructor
