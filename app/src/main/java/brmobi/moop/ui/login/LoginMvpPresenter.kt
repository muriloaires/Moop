package brmobi.moop.ui.login

import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 20/02/2018.
 */
interface LoginMvpPresenter<V : LoginMvpView> : MvpPresenter<V> {

    fun onLoginReady(phoneNumber: String, deviceToken: String, deviceType: String)

}