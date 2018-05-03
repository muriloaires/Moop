package brmobi.moop.ui.messages

import android.content.Intent
import brmobi.moop.data.network.model.Mensagem
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 25/02/2018.
 */
interface MessagesMvpPresenter<V : MessagesMvpView> : MvpPresenter<V> {

    fun handleIntent(intent: Intent)
    fun getMessages(): List<Mensagem>
    fun onBtnSendMessageClick(message: String)
    fun onViewReady()
    fun onMessageLongClick(position: Int): Boolean
    fun onDeleteMessageClick(position: Int)
}