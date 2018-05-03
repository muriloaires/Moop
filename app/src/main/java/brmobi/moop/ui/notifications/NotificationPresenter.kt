package brmobi.moop.ui.notifications

import brmobi.moop.data.DataManager
import brmobi.moop.notification.NotificationController
import brmobi.moop.data.network.model.Notificacao
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 22/02/2018.
 */
class NotificationPresenter<V : NotificationMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), NotificationMvpPresenter<V> {

    val mNotifications: MutableList<Notificacao> = mutableListOf()

    override fun onViewReady() {
        loadNotifications()
    }

    private fun loadNotifications() {
        mCompositeDisposable.add(dataManager.getNotifications(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ genericList ->
                    getMvpView()?.stopRefreshing()
                    mNotifications.clear()
                    mNotifications.addAll(genericList.data)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error )
                }))
    }

    override fun getNotifications(): List<Notificacao> {
        return mNotifications
    }

    override fun onNotificationClick(position: Int) {
        val notification = mNotifications[position]
        when (notification.tipo) {
            NotificationController.TIPO_NOVA_CURTIDA -> {
                getMvpView()?.showCommentsActivity(AppConstants.FEED_ID_PREF, notification.idObj)
            }
            NotificationController.TIPO_NOVO_COMENTARIO_FEED -> {
                getMvpView()?.showCommentsActivity(AppConstants.FEED_ID_PREF, notification.idObj)
            }
            NotificationController.TIPO_SOLICITACAO_MORADOR -> {
                getMvpView()?.showApproveDwellersActivity()
            }
        }
    }

    override fun onRefresh() {
        loadNotifications()
    }
}