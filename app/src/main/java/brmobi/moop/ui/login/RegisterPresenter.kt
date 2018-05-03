package brmobi.moop.ui.login

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Usuario
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.TextUtils
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by murilo aires on 20/02/2018.
 */
class RegisterPresenter<V : RegisterMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, mCompositeDisposable), RegisterMvpPresenter<V> {

    override fun onBtnCadastrarClick(name: String, email: String, phoneNumber: String?, avatarUrl: String?, loginType: String, deviceToken: String?, deviceType: String, avatar: File?) {
        when {
            name.isEmpty() -> getMvpView()?.onEmptyName(R.string.campo_obrigatorio)
            email.isEmpty() -> getMvpView()?.onInvalidEmail(R.string.email_invalido)
            !TextUtils.validEmail(email) -> getMvpView()?.onInvalidEmail(R.string.email_invalido)
            else -> {
                getMvpView()?.showLoading(R.string.aguarde, R.string.registrando)
                mCompositeDisposable.add(dataManager.doRegister(name, email, phoneNumber!!, deviceToken!!, deviceType, avatarUrl, avatar)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ usuario: Usuario ->
                            dataManager.updateUserInfo(usuario.apiToken, usuario.id, DataManager.LoginMode.LOGGE_IN_MODE_SERVER, usuario.nome, usuario.user.email, usuario.avatar)
                            getMvpView()?.hideLoading()
                            getMvpView()?.openMoopActivity()
                        }, { error ->
                            getMvpView()?.hideLoading()
                            handleApiError(error )
                        }))
            }
        }

    }
}