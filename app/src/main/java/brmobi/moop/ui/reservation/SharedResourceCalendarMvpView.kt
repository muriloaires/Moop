package brmobi.moop.ui.reservation

import brmobi.moop.data.network.model.DiaBemComum
import brmobi.moop.ui.base.MvpView
import java.io.Serializable
import java.util.*

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourceCalendarMvpView : MvpView {
    fun configureCalendarView(calendar: Calendar)
    fun configureDayDecorators(data: List<DiaBemComum>, calendar: Calendar)
    fun showAvailabilityFragment(dataExtraKey: String, dataExtraValue: String, sharedExtraKey: String, sharedValue: Serializable)
}