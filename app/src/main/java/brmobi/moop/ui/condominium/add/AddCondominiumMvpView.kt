package brmobi.moop.ui.condominium.add

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 26/02/2018.
 */
interface AddCondominiumMvpView : MvpView {
    fun showCondominiunsFragment()
    fun showDialogConfirmation(selectedCondominiumName: String, selectedBlocName: String, unity: String, profileResId: Int)
    fun finishWithOkResult()
}