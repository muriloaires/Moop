package brmobi.moop.ui.login

import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by murilo aires on 27/02/2018.
 */
class LoginActivityPresenter<V : LoginActivityMpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), LoginActivityMvpPresenter<V> {
}