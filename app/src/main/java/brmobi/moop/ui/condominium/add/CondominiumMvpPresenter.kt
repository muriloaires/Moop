package brmobi.moop.ui.condominium.add

import brmobi.moop.data.db.model.Condominio
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 26/02/2018.
 */
interface CondominiumMvpPresenter<V : CondominiumMvpView> : MvpPresenter<V> {
    fun afterTextChanged(zipcode: String)
    fun getCondominiuns(): List<Condominio>
    fun onCondominiumSelected(position: Int)
    fun onBtnForwardClick()
    fun onCondominiumNotFoundClick(zipCode: String)
}