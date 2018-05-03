package brmobi.moop.ui.reservation

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.ReservaBemComum
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 22/02/2018.
 */
class MyReservationsPresenter<V : MyReservationsMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), MyReservationsMvpPresenter<V> {

    private val mReservations: MutableList<ReservaBemComum> = mutableListOf()

    override fun onViewReady() {
    }

    override fun onResume() {
        loadReservations()
    }

    private fun loadReservations() {
        mCompositeDisposable.add(dataManager.getReservations(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ genericList ->
                    mReservations.clear()
                    mReservations.addAll(genericList.data)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error)
                }))
    }

    override fun onRefreshListener() {
        loadReservations()
    }

    override fun getReservations(): List<ReservaBemComum> {
        return mReservations
    }

    override fun onCancelReservationClick(position: Int) {
        getMvpView()?.showDialogCancelation(position)
    }

    override fun onConfirmCancelation(position: Int) {
        getMvpView()?.showLoading(R.string.aguarde, R.string.efetuando_requisicao)
        mCompositeDisposable.add(dataManager.cancelReservation(dataManager.getCurrentAccessToken(), mReservations[position].id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    mReservations.removeAt(position)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error )
                }))
    }
}