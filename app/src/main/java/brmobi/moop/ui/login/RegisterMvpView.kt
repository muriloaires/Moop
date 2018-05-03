package brmobi.moop.ui.login

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 20/02/2018.
 */
interface RegisterMvpView : MvpView {

    fun openMoopActivity()
    fun onEmptyName(resId: Int)
    fun onInvalidEmail(resId: Int)

}