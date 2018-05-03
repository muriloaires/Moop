package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.CondominiumRegister
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by murilo aires on 26/02/2018.
 */
class CondominiumRegisterPresenter<V : CondominiumRegisterMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), CondominiumRegisterMvpPresenter<V> {

    lateinit var condominiumRegister: CondominiumRegister

    override fun handleArgument(arguments: Bundle) {
        condominiumRegister = arguments.getSerializable(AppConstants.REGISTER_CONDOMINIUM_EXTRA_KEY) as CondominiumRegister
    }

    override fun onViewReady() {
        getMvpView()?.setZipCodeText(condominiumRegister.zipCode)
    }

    override fun onBtnRegisterCondominiumClick(zipCode: String, name: String, address: String, number: String, phoneNumber: String, isHorizontal: Boolean) {
        when {

            zipCode.isEmpty() -> getMvpView()?.showZipCodeError(R.string.campo_obrigatorio)
            zipCode.length < 10 -> getMvpView()?.showZipCodeError(R.string.cep_invalido)
            name.isEmpty() -> getMvpView()?.showCondominiumNameError(R.string.campo_obrigatorio)
            address.isEmpty() -> getMvpView()?.showCondominiumAddressError(R.string.campo_obrigatorio)
            number.isEmpty() -> getMvpView()?.showCondominiumNumberError(R.string.campo_obrigatorio)
            phoneNumber.isEmpty() -> getMvpView()?.showCondominiumPhoneNumberError(R.string.campo_obrigatorio)


            else -> {
                condominiumRegister.condominiumName = name
                condominiumRegister.condominiumAddress = address
                condominiumRegister.condominiumAddressNumber = number
                condominiumRegister.condominiumPhoneNumber = phoneNumber
                condominiumRegister.isHorizontal = isHorizontal
                getMvpView()?.showAddBlocFragment(condominiumRegister)
            }
        }

    }


}