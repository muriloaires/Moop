package brmobi.moop.ui.condominium.add

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 26/02/2018.
 */
interface AddBlocMvpView : MvpView {
    fun notifyDataSetChanged()
    fun setFields(condominiumName: String, condominiumZipCode: String, condominiumStreet: String, condominiumNumber: String)
    fun showBlocsFragment(condominiumId: Long, condominiumName: String)
}