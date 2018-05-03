package brmobi.moop.ui.reservation

import android.annotation.SuppressLint
import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.BemComum
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by murilo aires on 23/02/2018.
 */
class SharedResourceCalendarPresenter<V : SharedResourceCalendarMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), SharedResourceCalendarMvpPresenter<V> {

    private var calendar = Calendar.getInstance()
    private var mLastSelectedDate: Date? = null
    private lateinit var mSharedResource: BemComum

    override fun onViewReady() {
        getMvpView()?.configureCalendarView(calendar)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBtnReserveClick() {
        if (mLastSelectedDate != null) {
            val formattedDate: String = SimpleDateFormat("yyyy-MM-dd").format(mLastSelectedDate)
            getMvpView()?.showAvailabilityFragment(AppConstants.DATA_EXTRA_PARAMETER, formattedDate, AppConstants.SHARED_RESOURSE_EXTRA_KEY, mSharedResource)
        }else{
            getMvpView()?.showMessage(R.string.selecione_uma_data)
        }
    }

    override fun handleArguments(arguments: Bundle) {
        mSharedResource = arguments.getSerializable(AppConstants.SHARED_RESOURSE_EXTRA_KEY) as BemComum
    }

    override fun onCalendarConfigured() {
        loadSharedResourcesDays(calendar.time)
    }

    override fun onDateSelected(date: Date) {
        mLastSelectedDate = date
    }

    override fun onMonthChanged(date: Date) {
        calendar.time = date
        loadSharedResourcesDays(date)
    }

    private fun loadSharedResourcesDays(date: Date) {
        var calendar = Calendar.getInstance()
        calendar.time = date
        mCompositeDisposable.add(dataManager.getSharedResourcesDays(dataManager.getCurrentAccessToken(),
                dataManager.getLastSelectedCondominium(), mSharedResource.id,
                calendar[Calendar.MONTH] + 1, calendar[Calendar.YEAR])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.configureDayDecorators(it.data, calendar)
                }, {
                    handleApiError(it )
                }))

    }


}