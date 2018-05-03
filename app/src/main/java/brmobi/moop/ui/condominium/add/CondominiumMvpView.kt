package brmobi.moop.ui.condominium.add

import brmobi.moop.ui.base.MvpView
import java.io.Serializable

/**
 * Created by murilo aires on 26/02/2018.
 */
interface CondominiumMvpView : MvpView {
    fun showProgress()
    fun hideProgress()
    fun showBtnCondominiumNotFound()
    fun hideRecyclerView()
    fun hideBtnForward()
    fun showBtnForward()
    fun showRecyclerView()
    fun notifyDataSetChanged()
    fun setTextWatcherZipCode()
    fun showBlocsFragment(condominiumIdExtraKey: String, condominiumIdExtraValue: Long, condominiumNameExtraKey: String, condominiumNameExtraValue: String)
    fun showCondominiumRegisterFragment(condominiumRegister : Serializable)
}