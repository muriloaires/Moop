package brmobi.moop.ui.reservation

import brmobi.moop.ui.base.MvpView
import java.io.Serializable

/**
 * Created by murilo aires on 23/02/2018.
 */
interface SharedResourceDescriptionMvpView : MvpView {

    fun setSharedResourceName(nome: String)

    fun showSharedResourceTerms(termosDeUso: String)

    fun showSharedResourceImage(avatar: String?)

    fun showCalendarFragment(extraKey: String, extraSharedResource: Serializable)

}