package brmobi.moop.ui.messages

import android.content.Intent
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
 * Created by murilo aires on 25/02/2018.
 */
class MessagesPresenter<V : MessagesMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), MessagesMvpPresenter<V> {

    private val mMessages = mutableListOf<Mensagem>()
    private lateinit var userDestinyName: String
    private var userDestinyId: Long = -1L

    override fun handleIntent(intent: Intent) {
        userDestinyName = intent.getStringExtra(AppConstants.EXTRA_USER_DESTINATION_NAME)
        userDestinyId = intent.getLongExtra(AppConstants.EXTRA_USER_DESTINATION_ID, AppConstants.NULL_INDEX)
        getMvpView()?.setTitle(userDestinyName)
    }

    override fun getMessages(): List<Mensagem> {
        return mMessages
    }

    override fun onBtnSendMessageClick(message: String) {
        if (message.isNotEmpty()) {
            mCompositeDisposable.add(dataManager.postMessage(dataManager.getCurrentAccessToken(), userDestinyId, dataManager.getLastSelectedCondominium(), message)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mMessages.add(it)
                        getMvpView()?.clearEditText()
                        getMvpView()?.notifyDataSetChanged()
                    }, {
                        handleApiError(it )
                    }))
        }
    }

    override fun onViewReady() {
        mCompositeDisposable.add(dataManager.getChatMessages(dataManager.getCurrentAccessToken(), userDestinyId, dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mMessages.clear()
                    mMessages.addAll(it.data)
                    getMvpView()?.notifyDataSetChanged()
                }, {
                    handleApiError(it)
                }))
    }

    override fun onMessageLongClick(position: Int): Boolean {
        getMvpView()?.showMessageOptions(position)
        return true
    }

    override fun onDeleteMessageClick(position: Int) {
        mCompositeDisposable.add(dataManager.deleteMessage(dataManager.getCurrentAccessToken(), mMessages[position].id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mMessages.removeAt(position)
                    getMvpView()?.notifyDataSetChanged()
                }, {
                    handleApiError(it )
                }))
    }
}