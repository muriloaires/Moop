package brmobi.moop.ui.feed

import brmobi.moop.data.DataManager
import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.model.FeedItem
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
class FeedPresenter<V : FeedMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, mCompositeDisposable), FeedMvpPresenter<V> {

    private lateinit var mSelectedCondominium: Condominio
    private var items: MutableList<FeedItem?> = mutableListOf()
    private var indexToDelete: Int? = null

    init {
        items.add(0, null)
    }

    override fun onViewReady() {

        mCompositeDisposable.add(dataManager.loadCondominium(dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { condominium ->
                    mSelectedCondominium = condominium
                    loadFeed(0)
                })


    }

//    }

    private fun loadFeed(offset: Int) {
        if (mSelectedCondominium.isLiberado) {
            getMvpView()?.showRecycler()
            getMvpView()?.resetScrollListenerState()
            mCompositeDisposable.add(dataManager.getFeed(dataManager.getCurrentAccessToken(), mSelectedCondominium.id, 10, offset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ genericList ->
                        getMvpView()?.stopRefreshing()
                        setIsFromLoggedUser(genericList.data)
                        if (offset == 0) {
                            if (genericList.data.isEmpty()) {
                                getMvpView()?.showNoPublications()
                            } else {
                                getMvpView()?.showRecycler()
                            }

                            this.items.clear()
                            //for loading view
                            this.items.add(0, null)
                        }
                        this.items.addAll(this.items.size - 1, genericList.data)

                        if (genericList.data.size < 10) {
                            this.items.remove(null)
                            getMvpView()?.removeScrollListener()
                        }
                        getMvpView()?.notifyDataSetChanged()
                    }, { error ->
                        getMvpView()?.stopRefreshing()
                        error as HttpException
                        if (error.code() == 403) {
                            getMvpView()?.showLockedCondominium()
                        }

                    }))
        } else {
            getMvpView()?.stopRefreshing()
            getMvpView()?.showLockedCondominium()
        }
    }

    private fun setIsFromLoggedUser(items: List<FeedItem>) {
        for (feed: FeedItem in items) {
            feed.fromLoggedUser = feed.perfil.id == dataManager.getCurrentUserId()
        }
    }

    override fun getItems(): List<FeedItem?> {
        return items
    }

    override fun onBtnLikeClick(adapterPosition: Int) {
        items[adapterPosition]!!.isCurtidaPeloUsuario = true
        getMvpView()?.notifyDataSetChanged()

        mCompositeDisposable.add(dataManager.likeFeedItem(dataManager.getCurrentAccessToken(), items[adapterPosition]!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { error ->
                    items[adapterPosition]!!.isCurtidaPeloUsuario = false
                    getMvpView()?.notifyDataSetChanged()
                    handleApiError(error )
                }))
    }

    override fun onBtnUnlikeClick(adapterPosition: Int) {
        items[adapterPosition]!!.isCurtidaPeloUsuario = false
        getMvpView()?.notifyDataSetChanged()

        mCompositeDisposable.add(dataManager.unlikeFeedItem(dataManager.getCurrentAccessToken(), items[adapterPosition]!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { error ->
                    items[adapterPosition]!!.isCurtidaPeloUsuario = false
                    getMvpView()?.notifyDataSetChanged()
                    handleApiError(error )
                }))
    }


    override fun onOptionsClick(adapterPosition: Int) {
        indexToDelete = adapterPosition
        getMvpView()?.showFeedItemOptions()
    }

    override fun onDeleteItemFeedClick() {
        mCompositeDisposable.add(dataManager.deleteFeed(dataManager.getCurrentAccessToken(), items[indexToDelete!!]!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    items.removeAt(indexToDelete!!)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error )
                }))
    }

    override fun onRefreshUpdated() {
        loadFeed(0)
    }

    override fun onLoadMore() {
        loadFeed(items.size - 1)
    }

    override fun onNewPostActivitySuccessResult() {
        loadFeed(0)
    }

    override fun onFabCreatePostClick() {
        if (mSelectedCondominium.isLiberado) {
            getMvpView()?.openNewPostActivity()
        }
    }

    override fun btnCommentsClick(position: Int) {
        getMvpView()?.showCommentsActivity(AppConstants.FEED_ID_PREF, items[position]!!.id)
    }

    override fun onImgPostClick(position: Int) {
        getMvpView()?.showImagePostActivity(AppConstants.IMG_POST_EXTRA_KEY, items[position]!!.imagem)
    }
}