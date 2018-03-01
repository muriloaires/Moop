package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 26/02/2018.
 */
interface BlocMvpPresenter<V : BlocMvpView> : MvpPresenter<V> {
    fun handleArguments(arguments: Bundle)
    fun onViewReady()
    fun getBlocs(): List<Bloco>
    fun onBlocSelected(position: Int)
}