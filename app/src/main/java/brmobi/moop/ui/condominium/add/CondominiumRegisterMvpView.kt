package brmobi.moop.ui.condominium.add

import brmobi.moop.ui.base.MvpView
import java.io.Serializable

/**
 * Created by murilo aires on 26/02/2018.
 */
interface CondominiumRegisterMvpView : MvpView {
    fun setZipCodeText(zipCode: String)
    fun showAddBlocFragment(registerCondominium: Serializable)
    fun showZipCodeError(resId: Int)
    fun showCondominiumNameError(resId: Int)
    fun showCondominiumNumberError(resId: Int)
    fun showCondominiumAddressError(resId: Int)
    fun showCondominiumPhoneNumberError(resId: Int)
}