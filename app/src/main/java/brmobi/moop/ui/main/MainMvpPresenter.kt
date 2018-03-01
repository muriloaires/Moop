package brmobi.moop.ui.main

import android.content.Intent
import brmobi.moop.ui.base.MvpPresenter
import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 19/02/2018.
 */
interface MainMvpPresenter<V : MvpView> : MvpPresenter<V> {

    fun onViewReady(intent: Intent?)
    fun onNavDrawerSet()
    fun onNewIntent(intent: Intent)
    fun onMenuChamadosClick()
    fun onMenuDwellersClick()
    fun onApproveDwellersClick()
    fun onMyCondominiumClick()
    fun onManageCondominiumClick()
    fun onDetachCondominiumClick()
    fun onCondominiumSelected(position: Int)
    fun onEditProfileClick()
    fun onMenuAddCondominiumClick()
    fun onInviteMenuClick()
    fun onSupportMenuClick()
    fun onLogoutMenuClick()
    fun onCondominiumAdded()
    fun onProfileEdited()

}