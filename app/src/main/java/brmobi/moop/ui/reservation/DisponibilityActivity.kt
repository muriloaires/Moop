package brmobi.moop.ui.reservation

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_disponibilidades.*
import java.io.Serializable
import javax.inject.Inject

class DisponibilityActivity : BaseActivity(), DisponibilityMvpView {

    @Inject
    lateinit var mPresenter: DisponibilityMvpPresenter<DisponibilityMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disponibilidades)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        mPresenter.handleIntent(intent)
        mPresenter.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun setTitle(stringResId: Int, sharedResourceName: String) {
        toolbar.title = getString(stringResId) + " / " + sharedResourceName
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun showSharedResourceDescriptionFragment(extraKey: String, extraSharedResource: Serializable) {
        val ft = supportFragmentManager.beginTransaction()
        val fragment = SharedResourceDescriptionFragment.getInstance(extraKey, extraSharedResource)
        ft.replace(R.id.placeholder, fragment)
        ft.commit()
    }


    fun showCalendarioFragment(extraKey: String, extraSharedResource: Serializable) {
        val ft = supportFragmentManager.beginTransaction()
        val fragment = SharedResourceCalendarFragment.getInstance(extraKey, extraSharedResource)
        ft.replace(R.id.placeholder, fragment)
        ft.addToBackStack("main")
        ft.commit()
    }

    fun showDisponibilidadeFragment(dataExtraKey: String, dataExtraValue: String, sharedExtraKey: String, sharedValue: Serializable) {
        val ft = supportFragmentManager.beginTransaction()
        val fragment = AvailabilityFragment.getInstance(dataExtraKey, dataExtraValue, sharedExtraKey, sharedValue)
        ft.replace(R.id.placeholder, fragment)
        ft.addToBackStack("main")
        ft.commit()
    }
}
