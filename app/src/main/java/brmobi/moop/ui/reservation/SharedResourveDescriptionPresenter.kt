package brmobi.moop.ui.reservation

import android.os.Bundle
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
class SharedResourveDescriptionPresenter<V : SharedResourceDescriptionMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), SharedResourveDescriptionMvpPresenter<V> {

    lateinit var mSharedResource: BemComum
    override fun handleArguments(arguments: Bundle) {
        mSharedResource = arguments.getSerializable(AppConstants.SHARED_RESOURSE_EXTRA_KEY) as BemComum

    }

    override fun onViewReady() {
        getMvpView()?.setSharedResourceName(mSharedResource.nome)
        getMvpView()?.showSharedResourceImage(mSharedResource.avatar)
        getMvpView()?.showSharedResourceTerms(mSharedResource.termoDeUso)
    }

    override fun onBtnIAgreeClick() {
        getMvpView()?.showCalendarFragment(AppConstants.SHARED_RESOURSE_EXTRA_KEY, mSharedResource as Serializable)
    }
}