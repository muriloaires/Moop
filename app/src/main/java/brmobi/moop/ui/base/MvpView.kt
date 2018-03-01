package brmobi.moop.ui.base

import android.support.annotation.StringRes

/**
 * Created by murilo aires on 19/02/2018.
 */
interface MvpView {

    fun showLoading(titleStringResId: Int, messageStringResId: Int)

    fun hideLoading()

    fun openActivityOnTokenExpire()

    fun onError(@StringRes resId: Int)

    fun onError(error: String?)

    fun showMessage(message: String?)

    fun showMessage(@StringRes messageResId: Int)

    fun isNetworkConnected(): Boolean

    fun hideKeyboard()
}