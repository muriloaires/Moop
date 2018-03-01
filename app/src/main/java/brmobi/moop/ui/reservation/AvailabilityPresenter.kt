package brmobi.moop.ui.reservation

import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.BemComum
import brmobi.moop.data.network.model.DisponibilidadeBem
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 24/02/2018.
 */
class AvailabilityPresenter<V : AvailabilityMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), AvailabilityMvpPresenter<V> {

    private var mAvailabilities = mutableListOf<DisponibilidadeBem>()
    private lateinit var dayOfWeek: String
    private lateinit var mSharedResource: BemComum
    private lateinit var mData: String

    override fun getDayOfWeek(): String {
        return dayOfWeek
    }

    override fun getAvailabilities(): List<DisponibilidadeBem> = mAvailabilities

    override fun onViewReady() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.carregando_disponibilidades)
        mCompositeDisposable.add(dataManager.getResourceAvailability(dataManager.getCurrentAccessToken(), mSharedResource.id, mData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    mAvailabilities.clear()
                    mAvailabilities.addAll(it.data)
                    dayOfWeek = it.diaSemana
                    getMvpView()?.notifyDataSetChanged()

                }, {
                    getMvpView()?.hideLoading()
                    handleApiError(it as HttpException)
                }))
    }

    override fun onTextReserveClick(position: Int) {
        getMvpView()?.showLoading(R.string.aguarde, R.string.efetuando_reserva)
        mCompositeDisposable.add(dataManager.reserveAvailability(dataManager.getCurrentAccessToken(), mAvailabilities[position].id, mData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    getMvpView()?.showMessage(R.string.reserva_efetuada_com_sucesso)
                    getMvpView()?.finishWithOkResult()
                }, {
                    getMvpView()?.hideLoading()
                    handleApiError(it as HttpException)
                }))
    }

    override fun handleArguments(arguments: Bundle) {
        mSharedResource = arguments.getSerializable(AppConstants.SHARED_RESOURSE_EXTRA_KEY) as BemComum
        mData = arguments.getString(AppConstants.DATA_EXTRA_PARAMETER)
    }
}