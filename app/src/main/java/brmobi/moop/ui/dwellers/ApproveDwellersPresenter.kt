package brmobi.moop.ui.dwellers

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.PerfilHabitacional
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 22/02/2018.
 */
class ApproveDwellersPresenter<V : ApproveDwellersMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), ApproveDwellersMvpPresenter<V> {

    var mDwellers: MutableList<PerfilHabitacional> = mutableListOf()

    override fun getDwellers(): List<PerfilHabitacional> {
        return mDwellers
    }

    override fun onViewReady() {
        mCompositeDisposable.add(dataManager.getBlockedDwellers(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dwellers ->
                    mDwellers.clear()
                    mDwellers.addAll(dwellers)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error as HttpException)
                }))
    }

    override fun onBtnApproveClick(position: Int) {
        getMvpView()?.showDialogApprove(position)
    }

    override fun onBtnDesaproveClick(position: Int) {
        getMvpView()?.showDialogDesaprove(position)
    }

    override fun onDialogDesaproveClick(position: Int) {
        getMvpView()?.showLoading(R.string.aguarde, R.string.efetuando_requisicao)
        mCompositeDisposable.add(dataManager.approveDweller(dataManager.getCurrentAccessToken(), mDwellers[position].id, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    mDwellers.removeAt(position)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error as HttpException)
                }))
    }

    override fun onDialogApproveClick(position: Int) {
        getMvpView()?.showLoading(R.string.aguarde, R.string.efetuando_requisicao)
        mCompositeDisposable.add(dataManager.approveDweller(dataManager.getCurrentAccessToken(), mDwellers[position].id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    mDwellers.removeAt(position)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error as HttpException)
                }))
    }
}