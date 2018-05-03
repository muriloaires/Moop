package brmobi.moop.ui.tickets

import android.content.Intent
import android.net.Uri
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 21/02/2018.
 */
interface NewTicketMvpPresenter<V : NewTicketMvpView> : MvpPresenter<V> {

    fun onBtnNewTicketClick(title: String, message: String)
    fun onAddPhotoClick()
    fun onCameraSelected()
    fun onGallerySelected()
    fun onCameraIntentCreated(cameraIntent: Intent, requestedBy: Int)
    fun onCameraActivityReturn()
    fun onPhotoCropped(data: Intent)
    fun onPhotoFromGallerySelected(data: Uri?)

}