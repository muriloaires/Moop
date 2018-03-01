package brmobi.moop.ui.condominium

import brmobi.moop.ui.base.MvpView

/**
 * Created by murilo aires on 25/02/2018.
 */
interface MyCondominiumMvpView : MvpView {
    fun setSyindicLayoutVisibility(visibility: Int)
    fun setUserEmail(currentUserEmail: String)
    fun setCondominiumName(name: String)
    fun setCondominiumZipCode(zipCode: String)
    fun setCondominiumStreet(street: String)
    fun seCondominiumOrientation(resId: Int)
    fun setCondominiumOrientationDrawable(drawableResId: Int)
    fun setCondominiumSyndicName(syndicName: String)
    fun setCondominiumPhoneNumber(phone: String)
    fun setTotalDwellersText(totalDwellersCount: Int)
    fun showTexGeneratedPassword()
    fun showClickToCopyText()
    fun setNewPasswordTex(senha: String)
    fun openMoopSite(stringResId: Int)
    fun copyNewPassword()
}