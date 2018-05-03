package brmobi.moop.ui.main

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 19/02/2018.
 */
interface MainMvpView : MvpView {

    fun showNoneCondominiunsRegistered()
    fun setTitle(title: String?)
    fun clearNavMenu()
    fun createTopChannelMenu()
    fun addMenuItemToTopChannelMenu(nome: String?, position: Int, drawableResId: Int)
    fun setTopChannelMenuCheckable(position: Int)
    fun inflateMenuSindico(menuResId: Int)
    fun addNavOptionsMenu(menuResId: Int)
    fun configureTabs()
    fun selectTab(index: Int)
    fun openCommentsActivity(keyPref: String, value: Long)
    fun openApproveNewDwellerActivity()
    fun setUsername(username: String)
    fun setEmail(userEmail: String)
    fun setAvatarPlaceholder(drawableResId: Int)
    fun loadProfilePic(userProfilePic: String)
    fun addNewCondominiumMenu(stringResId: Int)
    fun openChamadosActivity()
    fun openDwellersActivity()
    fun openMyCondominiumActivity()
    fun showEditProfileActivity()
    fun openAddCondominiumActivity()
    fun createInviteIntent(stringResId: Int)
    fun showDialogSupport()

}