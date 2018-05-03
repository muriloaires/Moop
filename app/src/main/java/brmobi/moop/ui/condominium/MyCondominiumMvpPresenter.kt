package brmobi.moop.ui.condominium

import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 25/02/2018.
 */
interface MyCondominiumMvpPresenter<V : MyCondominiumMvpView> : MvpPresenter<V> {
    fun onViewReady()
    fun onBtnGeneratePasswordClick()
    fun onManageCondominiumClick()
    fun onNewPasswordClick()
}