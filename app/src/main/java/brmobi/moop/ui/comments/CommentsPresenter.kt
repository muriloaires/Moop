package brmobi.moop.ui.comments

import android.content.Intent
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Comentario
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 24/02/2018.
 */
class CommentsPresenter<V : CommentsMvpView> @Inject constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable), CommentsMvpPresenter<V> {

    private val mComments = mutableListOf<Comentario>()
    private var feedId: Long? = null

    override fun getComments(): List<Comentario> {
        return mComments
    }

    override fun handleIntent(intent: Intent) {
        feedId = intent.getLongExtra(AppConstants.FEED_ID_PREF, AppConstants.NULL_INDEX)
    }

    override fun onViewReady() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.loading_comments)
        mCompositeDisposable.add(dataManager.getComments(dataManager.getCurrentAccessToken(), feedId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    mComments.clear()
                    mComments.addAll(it.data)
                    getMvpView()?.notifyDataSetChanged()
                }, {
                    getMvpView()?.hideLoading()
                    handleApiError(it )
                }))
    }

    override fun onBtnSendCommentClick(text: String) {
        if (text.isNotEmpty()) {
            mCompositeDisposable.add(dataManager.postComment(dataManager.getCurrentAccessToken(), feedId!!, text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mComments.add(it)
                        getMvpView()?.clearEditTextComment()
                        getMvpView()?.notifyDataSetChanged()
                    }, {
                        handleApiError(it)
                    }))
        }
    }

    override fun onItemLongClick(position: Int): Boolean {
        val comment = mComments[position]
        if (comment.perfil.id == dataManager.getCurrentUserId()) {
            getMvpView()?.showDeleteOptions(position)
            return true
        }
        return false
    }

    override fun onDeleteCommentClick(position: Int) {
        mCompositeDisposable.add(dataManager.deleteComment(dataManager.getCurrentAccessToken(), mComments[position].id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mComments.removeAt(position)
                    getMvpView()?.notifyDataSetChanged()
                }, {
                    handleApiError(it as HttpException)
                }))
    }
}