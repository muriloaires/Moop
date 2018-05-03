package brmobi.moop.ui.base

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

/**
 * Created by murilo aires on 19/02/2018.
 */
interface MvpPresenter<V : MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

    fun handleApiError(error: Throwable)

    fun setUserAsLoggedOut()
}