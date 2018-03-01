package brmobi.moop.ui.tickets

import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.Chamado
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 21/02/2018.
 */
class TicketListPresenter<V : TicketListMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), TicketListMvpPresenter<V> {

    private val tickets: MutableList<Chamado> = mutableListOf()

    override fun onBtnCriarChamadoClick() {
        getMvpView()?.showNewTicketFragment()
    }

    override fun getTickets(): List<Chamado> = tickets

    override fun onResumeCalled() {
        getMvpView()?.showLoading(R.string.aguarde, R.string.loading_tickets)
        mCompositeDisposable.add(dataManager.getTickets(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ genericList ->
                    this.tickets.clear()
                    this.tickets.addAll(genericList.data)
                    if (tickets.isEmpty()) {
                        getMvpView()?.showNoTicketsView()
                    } else {
                        getMvpView()?.showTicketsView()
                    }
                    getMvpView()?.hideLoading()
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    getMvpView()?.hideLoading()
                    handleApiError(error as HttpException)
                }))
    }
}