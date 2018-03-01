package brmobi.moop.ui.reservation

import android.os.Bundle
import brmobi.moop.ui.base.MvpPresenter
import java.util.*

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourceCalendarMvpPresenter<V : SharedResourceCalendarMvpView> : MvpPresenter<V> {
    fun onViewReady()
    fun onDateSelected(date: Date)
    fun onMonthChanged(date: Date)
    fun handleArguments(arguments: Bundle)
    fun onBtnReserveClick()
    fun onCalendarConfigured()
}