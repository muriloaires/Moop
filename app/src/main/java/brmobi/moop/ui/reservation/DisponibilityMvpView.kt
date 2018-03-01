package brmobi.moop.ui.reservation

import brmobi.moop.ui.base.MvpView
import java.io.Serializable

/**
 * Created by murilo aires on 23/02/2018.
 */
interface DisponibilityMvpView : MvpView {
    fun setTitle(stringResId: Int, sharedResourceName: String)
    fun showSharedResourceDescriptionFragment(extraKey: String, extraSharedResource: Serializable)
}