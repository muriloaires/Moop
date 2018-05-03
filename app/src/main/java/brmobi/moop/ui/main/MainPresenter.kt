package brmobi.moop.ui.main

import android.content.Intent
import brmobi.moop.R
import brmobi.moop.data.DataManager
import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.ui.base.BasePresenter
import brmobi.moop.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by murilo aires on 19/02/2018.
 */
class MainPresenter<V : MainMvpView> @Inject constructor(dataManager: DataManager, mCompositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, mCompositeDisposable), MainMvpPresenter<V> {
    private var condominiuns: MutableList<Condominio> = mutableListOf()

    private var lastSelectedIndex = 0
    private var hasAction = false
    private var intent: Intent? = null

    override fun onViewReady(intent: Intent?) {
        this.intent = intent
        getCondominiuns()
    }

    private fun getCondominiuns() {
        mCompositeDisposable.add(dataManager.getUserCondominiuns(dataManager.getCurrentAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: GenericListResponse<Condominio> ->

                    mCompositeDisposable.add(dataManager.insertCondominiumList(response.data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                this.condominiuns.clear()
                                this.condominiuns.addAll(response.data)

                                configureCondominiuns()
                            })
                }, { err ->
                    handleApiError(err )
                }))
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun configureCondominiuns() {
        if (condominiuns.isEmpty()) {
            getMvpView()?.addNewCondominiumMenu(R.string.add_condominio)
            getMvpView()?.showNoneCondominiunsRegistered()
        } else {
            val lastSelectedCondominium = dataManager.getLastSelectedCondominium()
            if (lastSelectedCondominium == AppConstants.NULL_INDEX) {
                selectCondominium(0)
            } else {
                var posicao = 0
                for (i in condominiuns.indices) {
                    if (condominiuns[i].id == lastSelectedCondominium) {
                        posicao = i
                        break
                    }
                }
                selectCondominium(posicao)

                if (hasAction) {
                    handleIntent(intent)
                    hasAction = false
                }
            }
        }
    }

    override fun onNavDrawerSet() {
        val username = dataManager.getCurrentUserName()
        getMvpView()?.setUsername(username)
        val userEmail = dataManager.getCurrentUserEmail()
        getMvpView()?.setEmail(userEmail)
        val userProfilePic = dataManager.getCurrentProfilePic()
        if (userProfilePic.isNullOrEmpty()) {
            getMvpView()?.setAvatarPlaceholder(R.drawable.placeholder_avatar)
        } else {
            getMvpView()?.loadProfilePic(userProfilePic!!)
        }

    }

    private fun handleIntent(intent: Intent?) {
        val action = intent!!.getStringExtra(AppConstants.ACTION_EXTRA_PARAMETER)
        if (action != null) {
            when (action) {
                AppConstants.ACTION_MESSAGES -> {
                    val condominiumId = intent.getLongExtra(AppConstants.CONDOMINIO_ID_EXTRA_KEY, AppConstants.NULL_INDEX)
                    if (condominiuns.isNotEmpty()) {
                        if (dataManager.getLastSelectedCondominium() == condominiumId) {
                            getMvpView()?.selectTab(2)
                            TODO("CALL LOADULTIMASMENSAGENS FROM MESSAGES FRAGMENT")
                        } else {
                            var position = -1
                            for (i in condominiuns.indices) {
                                val condominium = condominiuns[i]
                                if (condominium.id == condominiumId) {
                                    position = i
                                    break
                                }
                            }
                            if (position != -1) {
                                selectCondominium(position)
                                getMvpView()?.selectTab(2)
                            }
                        }
                    } else {
                        hasAction = true
                    }
                }
                AppConstants.ACTION_COMMENTS -> {
                    getMvpView()?.openCommentsActivity(AppConstants.FEED_ID_PREF, intent.getLongExtra(AppConstants.FEED_ID_PREF, AppConstants.NULL_INDEX))
                }
                AppConstants.ACTION_NEW_DWELLER -> {
                    val condominiumId = intent.getLongExtra(AppConstants.CONDOMINIO_ID_EXTRA_KEY, AppConstants.NULL_INDEX)
                    if (condominiuns.isNotEmpty()) {
                        if (dataManager.getLastSelectedCondominium() == condominiumId) {
                            getMvpView()?.selectTab(2)
                            getMvpView()?.openApproveNewDwellerActivity()
                        } else {
                            var position = -1
                            for (i in condominiuns.indices) {
                                val condominium = condominiuns[i]
                                if (condominium.id == condominiumId) {
                                    position = i
                                    break
                                }
                            }
                            if (position != -1) {
                                selectCondominium(position)
                                getMvpView()?.selectTab(2)
                            }
                        }
                    } else {
                        hasAction = true
                    }

                }

                AppConstants.NEW_DWELLER_APPROVED -> getCondominiuns()
            }
        }
    }

    override fun onEditProfileClick() {
        getMvpView()?.showEditProfileActivity()
    }

    override fun onMenuChamadosClick() {
        getMvpView()?.openChamadosActivity()
    }

    override fun onMenuDwellersClick() {
        getMvpView()?.openDwellersActivity()
    }

    override fun onApproveDwellersClick() {
        getMvpView()?.openApproveNewDwellerActivity()
    }

    override fun onMyCondominiumClick() {
        getMvpView()?.openMyCondominiumActivity()
    }

    override fun onManageCondominiumClick() {
        getMvpView()?.openMyCondominiumActivity()
    }

    override fun onInviteMenuClick() {
        getMvpView()?.createInviteIntent(R.string.invite_text)
    }

    override fun onSupportMenuClick() {
        getMvpView()?.showDialogSupport()
    }

    override fun onLogoutMenuClick() {
        dataManager.setUserAsLoggedOut()
        dataManager.clearPreferences()
        getMvpView()?.openActivityOnTokenExpire()
    }

    override fun onDetachCondominiumClick() {
        mCompositeDisposable.add(dataManager.doDetachFromCondominium(dataManager.getCurrentAccessToken(), dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    for (condominio in condominiuns) {
                        if (condominio.id == dataManager.getLastSelectedCondominium()) {
                            condominiuns.remove(condominio)
                            break
                        }
                    }
                    dataManager.saveLastSelectedCondominium(AppConstants.NULL_INDEX)
                    configureCondominiuns()
                }, { err ->
                    handleApiError(err )
                })
        )
    }

    override fun onMenuAddCondominiumClick() {
        getMvpView()?.openAddCondominiumActivity()
    }

    override fun onCondominiumSelected(position: Int) {
        selectCondominium(position)
    }

    private fun selectCondominium(position: Int) {
        lastSelectedIndex = position
        val selectedCondominium = condominiuns[position]
        dataManager.saveLastSelectedCondominium(selectedCondominium.id)
        getMvpView()?.setTitle(selectedCondominium.nome)
        getMvpView()?.clearNavMenu()
        getMvpView()?.createTopChannelMenu()
        for (i in condominiuns.indices) {
            val condominium: Condominio = condominiuns[i]
            getMvpView()?.addMenuItemToTopChannelMenu(condominium.nome, i, if (condominium.isHorizontal) R.drawable.ic_house else R.drawable.ic_predio)
        }
        getMvpView()?.setTopChannelMenuCheckable(position)
        dataManager.loadCondominium(dataManager.getLastSelectedCondominium())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { condominium ->
                    if (condominium.isSindico) {
                        getMvpView()?.inflateMenuSindico(R.menu.activity_moop_drawer_sindico)
                    }
                    getMvpView()?.addNewCondominiumMenu(R.string.add_condominio)
                    getMvpView()?.addNavOptionsMenu(R.menu.activity_moop_drawer)
                    getMvpView()?.configureTabs()
                }
    }

    override fun onCondominiumAdded() {
        getCondominiuns()
    }

    override fun onProfileEdited() {
        onNavDrawerSet()
    }
}