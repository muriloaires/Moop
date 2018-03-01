package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 26/02/2018.
 */
interface AddBlocMvpPresenter<V : AddBlocMvpView> : MvpPresenter<V> {
    fun onAddBlocoClick()
    fun getBlocs(): List<String>
    fun onBtnDeleteBlocClick(position: Int)
    fun onBtnAddBlockClick(position: Int)
    fun onTextChanged(position: Int, charSequence: CharSequence)
    fun handleArguments(arguments: Bundle)
    fun onViewReady()
}