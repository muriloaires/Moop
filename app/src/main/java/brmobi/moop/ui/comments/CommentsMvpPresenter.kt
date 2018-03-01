package brmobi.moop.ui.comments

import android.content.Intent
import brmobi.moop.data.network.model.Comentario
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 24/02/2018.
 */
interface CommentsMvpPresenter<V : CommentsMvpView> : MvpPresenter<V> {
    fun getComments(): List<Comentario>
    fun handleIntent(intent: Intent)
    fun onViewReady()
    fun onBtnSendCommentClick(text: String)
    fun onItemLongClick(position: Int): Boolean
    fun onDeleteCommentClick(position: Int)
}