package brmobi.moop.ui.condominium

import android.view.View
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 25/02/2018.
 */
class MyCondominiumPresenter<V : MyCondominiumMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), MyCondominiumMvpPresenter<V> {

    override fun onViewReady() {
        mCompositeDisposable.add(dataManager.loadCondominium(dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { condominium ->
                    getMvpView()?.setSyindicLayoutVisibility(if (condominium.isSindico) View.VISIBLE else View.GONE)
                    getMvpView()?.setUserEmail(dataManager.getCurrentUserEmail())
                    getDetail()
                })
    }

    private fun getDetail() {
        mCompositeDisposable.add(dataManager.getCondominiumDetail(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ condominium ->
                    getMvpView()?.setCondominiumName(condominium.nome)
                    getMvpView()?.setCondominiumZipCode(condominium.cep)
                    getMvpView()?.setCondominiumStreet(condominium.logradouro)
                    getMvpView()?.seCondominiumOrientation(if (condominium.isHorizontal) R.string.horizontal else R.string.vertical)
                    getMvpView()?.setCondominiumOrientationDrawable(if (condominium.isHorizontal) R.drawable.ic_house else R.drawable.ic_predio)
                    getMvpView()?.setCondominiumSyndicName(condominium.sindico.nome)
                    getMvpView()?.setCondominiumPhoneNumber(condominium.telefone)
                    getMvpView()?.setTotalDwellersText(condominium.totalMoradores)
                }, {

                }))
    }

    override fun onBtnGeneratePasswordClick() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.generating_new_password)
        mCompositeDisposable.add(dataManager.generateNewPassword(dataManager.getCurrentAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    getMvpView()?.showTexGeneratedPassword()
                    getMvpView()?.showClickToCopyText()
                    getMvpView()?.setNewPasswordTex(it.senha)
                }, {
                    getMvpView()?.hideLoading()
                    handleApiError(it )
                }))
    }

    override fun onManageCondominiumClick() {
        getMvpView()?.openMoopSite(R.string.site_moop)
    }

    override fun onNewPasswordClick() {
        getMvpView()?.copyNewPassword()
    }
}