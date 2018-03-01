package brmobi.moop.ui.condominium.add

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.model.CondominiumRegister
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 26/02/2018.
 */
class CondominiumPresenter<V : CondominiumMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), CondominiumMvpPresenter<V> {

    val mCondominiuns = mutableListOf<Condominio>()
    var lastCondominiumSelected = -1

    override fun afterTextChanged(zipCode: String) {
        getMvpView()?.showProgress()
        mCompositeDisposable.add(dataManager.getCondominiumFromZipCode(dataManager.getCurrentAccessToken(), zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideProgress()
                    mCondominiuns.clear()
                    mCondominiuns.addAll(it.data)
                    getMvpView()?.setTextWatcherZipCode()
                    getMvpView()?.notifyDataSetChanged()
                    getMvpView()?.showBtnCondominiumNotFound()
                    lastCondominiumSelected = -1
                    if (mCondominiuns.isEmpty()) {
                        getMvpView()?.showMessage(R.string.nenhum_condominio_encontrado)
                        getMvpView()?.hideRecyclerView()
                        getMvpView()?.hideBtnForward()
                    } else {
                        getMvpView()?.showRecyclerView()
                    }
                }, {
                    getMvpView()?.hideProgress()
                    getMvpView()?.setTextWatcherZipCode()
                    handleApiError(it as HttpException)
                }))
    }

    override fun getCondominiuns(): List<Condominio> {
        return mCondominiuns
    }

    override fun onCondominiumSelected(position: Int) {
        getMvpView()?.showBtnForward()
        if (lastCondominiumSelected != -1) {
            mCondominiuns[lastCondominiumSelected].isSelected = false
        }
        mCondominiuns[position].isSelected = true
        lastCondominiumSelected = position
        getMvpView()?.notifyDataSetChanged()
    }

    override fun onBtnForwardClick() {
        if (lastCondominiumSelected == -1) {
            getMvpView()?.showMessage(R.string.escolha_um_condominio)
        } else {
            getMvpView()?.showBlocsFragment(AppConstants.CONDOMINIO_ID_EXTRA_KEY, mCondominiuns[lastCondominiumSelected].id, AppConstants.CONDOMINIO_NAME_EXTRA_KEY, mCondominiuns[lastCondominiumSelected].nome)
        }
    }

    override fun onCondominiumNotFoundClick(zipCode: String) {
        val condominiumRegister = CondominiumRegister(zipCode, null, null, null, null, null)
        getMvpView()?.showCondominiumRegisterFragment(condominiumRegister)
    }
}