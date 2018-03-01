package brmobi.moop.ui.reservation

import android.os.Bundle
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourveDescriptionMvpPresenter<V : SharedResourceDescriptionMvpView> : MvpPresenter<V> {
    fun handleArguments(arguments: Bundle)
    fun onViewReady()
    fun onBtnIAgreeClick()

}