package brmobi.moop.ui.dwellers

import brmobi.moop.data.network.model.PerfilHabitacional
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 22/02/2018.
 */
interface ApproveDwellersMvpPresenter<V : ApproveDwellersMvpView> : MvpPresenter<V> {

    fun getDwellers(): List<PerfilHabitacional>
    fun onViewReady()
    fun onBtnApproveClick(position: Int)
    fun onBtnDesaproveClick(position: Int)
    fun onDialogDesaproveClick(position: Int)
    fun onDialogApproveClick(position: Int)

}