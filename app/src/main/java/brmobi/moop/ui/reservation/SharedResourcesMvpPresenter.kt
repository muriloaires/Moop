package brmobi.moop.ui.reservation

import brmobi.moop.data.network.model.BemComum
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourcesMvpPresenter<V : SharedResourcesMvpView> : MvpPresenter<V> {

    fun getSharedResources(): List<BemComum?>

    fun onSharedResourceClick(position: Int)

    fun onViewReady()

    fun onSharedResourceReserved()

    fun onRefreshListener()

}