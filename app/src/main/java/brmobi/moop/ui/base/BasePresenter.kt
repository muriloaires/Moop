package brmobi.moop.ui.base

import brmobi.moop.R
import brmobi.moop.data.DataManager
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by murilo aires on 19/02/2018.
 */
open class BasePresenter<V : MvpView> @Inject constructor(val dataManager: DataManager, val mCompositeDisposable: CompositeDisposable) : MvpPresenter<V> {

    private var mMvpView: V? = null

    override fun onAttach(mvpView: V) {
        mMvpView = mvpView
    }

    override fun onDetach() {
        mCompositeDisposable.dispose()
        mMvpView = null
    }

    override fun handleApiError(error: Throwable) {
        if (error is HttpException) {
            when (error.code()) {
                401 -> {
                    setUserAsLoggedOut()
                    getMvpView()?.openActivityOnTokenExpire()
                }
                500 -> getMvpView()?.onError(R.string.algo_errado_ocorreu)
                else -> {
                    try {
                        val json = error.response().errorBody()?.string()
                        getMvpView()?.onError(JSONObject(json).getString("message"))
                    } catch (e: Exception) {
                        getMvpView()?.onError(R.string.algo_errado_ocorreu)
                    }
                }

            }
        } else {
            getMvpView()?.onError(R.string.algo_errado_ocorreu)
        }
    }

    override fun setUserAsLoggedOut() {
        dataManager.setAccessToken(null)
    }

    fun isViewAttached(): Boolean {
        return mMvpView != null
    }

    fun getMvpView(): V? {
        return mMvpView
    }
}