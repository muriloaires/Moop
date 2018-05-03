package brmobi.moop.ui.condominium.add

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 26/02/2018.
 */
class AddCondominiumPresenter<V : AddCondominiumMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), AddCondominiumMvpPresenter<V> {

    var zipCode: String? = null
    var name: String? = null
    private lateinit var mBloc: Bloco
    var selectedCondominiumId = -1L
    private lateinit var selectedUnity: String
    private var isOwner: Boolean = false
    private lateinit var selectedCondominiumName: String

    override fun onViewReady() {
        getMvpView()?.showCondominiunsFragment()
    }

    override fun onShowBlocsFragment(condominiumIdExtraValue: Long, condominiumNameExtraValue: String) {
        this.selectedCondominiumId = condominiumIdExtraValue
        this.selectedCondominiumName = condominiumNameExtraValue
    }


    override fun onShowDialogOwnerDweller(mSelectedBloc: Bloco) {
        this.mBloc = mSelectedBloc
    }

    override fun onDialogDwellerOwnerConfirmClick(number: String, dweller: Boolean): Boolean {
        return if (number.isEmpty()) {
            getMvpView()?.showMessage(R.string.insira_o_numero_unidade)
            false

        } else {
            selectedUnity = number
            isOwner = !dweller
            getMvpView()?.showDialogConfirmation(selectedCondominiumName, mBloc.nome, number, if (!dweller) R.string.proprietario else R.string.morador)
            true
        }
    }


    override fun onConfirmRegistration() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.registrando_unidade)
        mCompositeDisposable.add(dataManager.doRegisterUnit(dataManager.getCurrentAccessToken(), mBloc.id, isOwner, !isOwner, selectedUnity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    dataManager.saveLastSelectedCondominium(selectedCondominiumId)
                    getMvpView()?.finishWithOkResult()
                }, {
                    getMvpView()?.hideLoading()
                    handleApiError(it)
                }))
    }
}