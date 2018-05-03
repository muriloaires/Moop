package brmobi.moop.ui.notifications

import brmobi.moop.data.network.model.Notificacao
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 22/02/2018.
 */
interface NotificationMvpPresenter<V : NotificationMvpView> : MvpPresenter<V> {
    fun onViewReady()

    fun getNotifications(): List<Notificacao>

    fun onNotificationClick(position: Int)

    fun onRefresh()
}