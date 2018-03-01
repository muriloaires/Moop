package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.CondominiumRegister
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 26/02/2018.
 */
class AddBlocPresenter<V : AddBlocMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), AddBlocMvpPresenter<V> {

    private val mBlocs = mutableListOf<String>()

    init {
        mBlocs.add("")
    }

    lateinit var registerCondominium: CondominiumRegister

    override fun onAddBlocoClick() {
        mBlocs.remove("")
        if (mBlocs.isEmpty()) {
            getMvpView()?.showMessage(R.string.nenhum_bloco)
            mBlocs.add("")
        } else {
            registerCondominium()
        }
    }

    private fun registerCondominium() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.cadastrando_condominio)
        mCompositeDisposable.add(dataManager.doRegisterCondominium(dataManager.getCurrentAccessToken(),
                registerCondominium.zipCode, registerCondominium.condominiumName!!, registerCondominium.condominiumAddress!!,
                registerCondominium.condominiumAddressNumber!!, registerCondominium.condominiumPhoneNumber!!,
                registerCondominium.isHorizontal!!, mBlocs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    getMvpView()?.showBlocsFragment(it.data.id, it.data.nome)
                }, {

                }))

    }

    override fun getBlocs(): List<String> {
        return mBlocs
    }

    override fun onBtnDeleteBlocClick(position: Int) {
        mBlocs.removeAt(position)
        getMvpView()?.notifyDataSetChanged()
    }

    override fun onBtnAddBlockClick(position: Int) {
        mBlocs.add("")
        getMvpView()?.notifyDataSetChanged()
    }

    override fun onTextChanged(position: Int, charSequence: CharSequence) {
        mBlocs[position] = charSequence.toString()
    }

    override fun handleArguments(arguments: Bundle) {
        registerCondominium = arguments.getSerializable(AppConstants.REGISTER_CONDOMINIUM_EXTRA_KEY) as CondominiumRegister

    }

    override fun onViewReady() {
        getMvpView()?.setFields(registerCondominium.condominiumName!!, registerCondominium.zipCode, registerCondominium.condominiumAddress!!,
                registerCondominium.condominiumAddressNumber!!)
    }
}