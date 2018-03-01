package brmobi.moop.ui.reservation

import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.BemComum
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 23/02/2018.
 */
class SharedResourcesPresenter<V : SharedResourcesMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), SharedResourcesMvpPresenter<V> {

    private val mSharedResources: MutableList<BemComum?> = mutableListOf()

    override fun getSharedResources(): List<BemComum?> {
        return mSharedResources
    }


    override fun onSharedResourceClick(position: Int) {
        val selectedSharedResource = mSharedResources[position]!!
        getMvpView()?.openAvailableActivity(AppConstants.SHARED_RESOURSE_EXTRA_KEY, selectedSharedResource)
    }

    override fun onViewReady() {
        mCompositeDisposable.add(dataManager.loadCondominium(dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { condominio ->
                    if (!condominio.isLiberado) {
                        getMvpView()?.showAutorizationView()
                    } else {
                        getMvpView()?.showRecyclerView()
                        loadSharedResources()
                    }
                })
    }

    private fun loadSharedResources() {
        mCompositeDisposable.add(dataManager.getSharedResources(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ genericList ->
                    mSharedResources.clear()
                    mSharedResources.add(0, null)
                    mSharedResources.addAll(genericList.data)
                    getMvpView()?.stopRefreshing()
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    getMvpView()?.stopRefreshing()
                    handleApiError(error as HttpException)
                }))
    }

    override fun onSharedResourceReserved() {
        getMvpView()?.selectMyReservationsFragment()
    }

    override fun onRefreshListener() {
        loadSharedResources()
    }
}