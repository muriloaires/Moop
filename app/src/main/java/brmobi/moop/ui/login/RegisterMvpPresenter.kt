package brmobi.moop.ui.login

import brmobi.moop.ui.base.MvpPresenter
import java.io.File

/**
 * Created by murilo aires on 20/02/2018.
 */
interface RegisterMvpPresenter<V : RegisterMvpView> : MvpPresenter<V> {
    fun onBtnCadastrarClick(name: String, email: String, phoneNumber: String?, avatarUrl: String?, loginType: String, deviceToken: String?, deviceType: String, avatar: File?)
}