package brmobi.moop.ui.splash

import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 25/02/2018.
 */
interface SplashMvpPresenter<V : SplasMvpView> : MvpPresenter<V> {
    fun onViewReady()
}