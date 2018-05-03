package brmobi.moop.ui.dwellers

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 21/02/2018.
 */
interface DwellersMvpView : MvpView {
    fun notifyDataSetChanged()
    fun openMessageActivity(dwellerId: Long, nome: String)
}