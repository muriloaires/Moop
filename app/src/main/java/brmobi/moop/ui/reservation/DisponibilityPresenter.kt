package brmobi.moop.ui.reservation

import android.content.Intent
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.BemComum
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by murilo aires on 23/02/2018.
 */
class DisponibilityPresenter<V : DisponibilityMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), DisponibilityMvpPresenter<V> {

    lateinit var sharedResource: BemComum
    override fun handleIntent(intent: Intent) {
        sharedResource = intent.getSerializableExtra(AppConstants.SHARED_RESOURSE_EXTRA_KEY) as BemComum
        getMvpView()?.setTitle(R.string.reservations, sharedResource.nome)

    }

    override fun onViewReady() {
        getMvpView()?.showSharedResourceDescriptionFragment(AppConstants.SHARED_RESOURSE_EXTRA_KEY, sharedResource as Serializable)
    }
}