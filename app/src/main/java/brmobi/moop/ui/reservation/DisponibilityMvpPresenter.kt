package brmobi.moop.ui.reservation

import android.content.Intent
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 23/02/2018.
 */
interface DisponibilityMvpPresenter<V : DisponibilityMvpView> : MvpPresenter<V> {

    fun handleIntent(intent: Intent)

    fun onViewReady()

}