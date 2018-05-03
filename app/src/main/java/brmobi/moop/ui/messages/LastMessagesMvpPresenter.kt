package brmobi.moop.ui.messages

import brmobi.moop.data.network.model.Mensagem
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 21/02/2018.
 */
interface LastMessagesMvpPresenter<V : LastMessagesMvpView> : MvpPresenter<V> {

    fun getList(): List<Mensagem>
    fun onViewReady()
    fun onFabClick()
    fun onRefreshListener()
    fun openMessagesActivity(position: Int)

}