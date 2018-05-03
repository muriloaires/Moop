package brmobi.moop.ui.condominium.add

import android.os.Bundle
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 26/02/2018.
 */
class BlocPresenter<V : BlocMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), BlocMvpPresenter<V> {

    val mBlocs = mutableListOf<Bloco>()
    var condominiumId = -1L
    private lateinit var mSelectedBloc: Bloco
    override fun handleArguments(arguments: Bundle) {
        condominiumId = arguments.getLong(AppConstants.CONDOMINIO_ID_EXTRA_KEY)
    }

    override fun onViewReady() {
        mCompositeDisposable.add(dataManager.getCondominiumBlocs(dataManager.getCurrentAccessToken(), condominiumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mBlocs.clear()
                    mBlocs.addAll(it.data)
                    getMvpView()?.notifyDataSetChanged()
                }, {
                    handleApiError(it)
                }))
    }

    override fun getBlocs(): List<Bloco> {
        return mBlocs
    }


    override fun onBlocSelected(position: Int) {
        mSelectedBloc = mBlocs[position]
        getMvpView()?.showDialogOwnerDweller(mSelectedBloc)
    }
}