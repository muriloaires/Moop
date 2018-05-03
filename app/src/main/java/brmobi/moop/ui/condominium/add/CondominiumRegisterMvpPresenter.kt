package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 26/02/2018.
 */
interface CondominiumRegisterMvpPresenter<V : CondominiumRegisterMvpView> : MvpPresenter<V> {
    fun handleArgument(arguments: Bundle)
    fun onViewReady()
    fun onBtnRegisterCondominiumClick(zipCode: String, name: String, address: String, number: String, phoneNumber: String, isHorizontal: Boolean)
}