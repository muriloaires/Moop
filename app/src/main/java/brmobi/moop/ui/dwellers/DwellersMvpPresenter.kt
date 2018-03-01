package brmobi.moop.ui.dwellers

import brmobi.moop.data.network.model.PerfilHabitacional
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 21/02/2018.
 */
interface DwellersMvpPresenter<V : DwellersMvpView> : MvpPresenter<V> {

    fun getDwellers(): List<PerfilHabitacional>

    fun onViewReady()

    fun onMenuItemActionCollapse()

    fun onTextQuerySubmit(query: String)

    fun onQueryTextChange(newText: String)

    fun openMessageActivity(position: Int)


}