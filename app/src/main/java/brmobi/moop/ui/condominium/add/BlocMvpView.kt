package brmobi.moop.ui.condominium.add

import brmobi.moop.data.network.model.Bloco
import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 26/02/2018.
 */
interface BlocMvpView : MvpView {
    fun notifyDataSetChanged()
    fun showDialogOwnerDweller(mSelectedBloc: Bloco)
}