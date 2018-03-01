package brmobi.moop.ui.messages

import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Mensagem
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 21/02/2018.
 */
class LastMessagesPresenter<V : LastMessagesMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, mCompositeDisposable), LastMessagesMvpPresenter<V> {
    val messages: MutableList<Mensagem> = mutableListOf()

    override fun getList(): List<Mensagem> {
        return messages
    }

    override fun onViewReady() {
        loadLastMessages()
    }

    private fun loadLastMessages() {
        mCompositeDisposable.add(dataManager.getUserLastMessages(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ genericList ->
                    configureMessages(genericList.data)
                    this.messages.clear()
                    this.messages.addAll(genericList.data)
                    getMvpView()?.notifyDataSetChanged()

                }, { error ->
                    handleApiError(error as HttpException)
                }))
    }

    private fun configureMessages(messages: List<Mensagem>) {
        for (message: Mensagem in messages) {
            message.fromLoggedUser = message.dePerfil.id == dataManager.getCurrentUserId()
            message.otherUser = if (message.fromLoggedUser) message.paraPerfil else message.dePerfil
        }
    }

    override fun onFabClick() {
        getMvpView()?.showDwellersActivity()
    }

    override fun onRefreshListener() {
        loadLastMessages()
    }

    override fun openMessagesActivity(position: Int) {
        getMvpView()?.openMessagesActivity(AppConstants.EXTRA_USER_DESTINATION_ID, messages[position].otherUser.id, AppConstants.EXTRA_USER_DESTINATION_NAME, messages[position].otherUser.nome)
    }
}