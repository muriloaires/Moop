package brmobi.moop.ui.login

import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 20/02/2018.
 */
class LoginPresenter<V : LoginMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, mCompositeDisposable), LoginMvpPresenter<V> {

    override fun onLoginReady(phoneNumber: String, token: String, deviceType: String) {
        mCompositeDisposable.add(dataManager.doLogin(phoneNumber, token, deviceType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ usuario ->
                    dataManager.updateUserInfo(usuario.apiToken, usuario.id, DataManager.LoginMode.LOGGE_IN_MODE_SERVER, usuario.nome, usuario.user.email, usuario.avatar)
                    getMvpView()?.openMoopActivity()
                }, { err ->
                    err as HttpException
                    if (err.code() == 404) {
                        getMvpView()?.showRegisterFragment()
                    } else {
                        handleApiError(err)
                    }
                }))
    }
}