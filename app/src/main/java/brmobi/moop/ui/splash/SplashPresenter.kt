package brmobi.moop.ui.splash

import android.os.Handler
import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by murilo aires on 25/02/2018.
 */
class SplashPresenter<V : SplasMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), SplashMvpPresenter<V> {

    override fun onViewReady() {
        val handler = Handler()
        handler.postDelayed({
            if (dataManager.getCurrentLoginMode() == DataManager.LoginMode.LOGGE_IN_MODE_SERVER.mType) {
                getMvpView()?.openMoopActivity()
            } else {
                getMvpView()?.openLoginActivity()
            }
        }, 1500)

    }
}