package brmobi.moop.ui.condominium.add

import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 26/02/2018.
 */
interface AddCondominiumMvpPresenter<V : AddCondominiumMvpView> : MvpPresenter<V> {
    fun onViewReady()
    fun onShowBlocsFragment(condominiumIdExtraValue: Long, condominiumNameExtraValue: String)
    fun onShowDialogOwnerDweller(mSelectedBloc: Bloco)
    fun onDialogDwellerOwnerConfirmClick(number: String, dweller: Boolean): Boolean
    fun onConfirmRegistration()
}