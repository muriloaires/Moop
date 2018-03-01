package brmobi.moop.ui.dwellers

import brmobi.moop.data.DataManager
import brmobi.moop.data.network.model.PerfilHabitacional
import brmobi.moop.ui.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 21/02/2018.
 */
class DwellersPresenter<V : DwellersMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, mCompositeDisposable), DwellersMvpPresenter<V> {

    private var dwellers: MutableList<PerfilHabitacional> = mutableListOf()

    override fun getDwellers(): List<PerfilHabitacional> {
        return dwellers
    }

    override fun onViewReady() {
        loadDwellers("")
    }

    private fun loadDwellers(name: String) {
        mCompositeDisposable.add(dataManager.getDwellers(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium(), name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    this.dwellers.clear()
                    this.dwellers.addAll(list)
                    getMvpView()?.notifyDataSetChanged()
                }, { error ->
                    handleApiError(error as HttpException)
                }))
    }

    override fun onMenuItemActionCollapse() {
        loadDwellers("")
    }

    override fun onTextQuerySubmit(query: String) {
        loadDwellers(query)
    }

    override fun onQueryTextChange(newText: String) {
        if (newText.length > 3) {
            loadDwellers(newText)
        }
    }

    //    @OnClick(R.id.btn_enviar_mensagem)
//    fun btnEnviarMensagemAction(view: View) {
//        val intent = Intent(context, MessagesActivity::class.java)
//        intent.putExtra("usuarioDestinoId", morador!!.perfil.id)
//        intent.putExtra("usuarioDestinoNome", morador!!.perfil.nome)
//        context.startActivity(intent)
//    }
    override fun openMessageActivity(position: Int) {
        getMvpView()?.openMessageActivity(dwellers[position].perfil.id, dwellers[position].perfil.nome)
    }
}