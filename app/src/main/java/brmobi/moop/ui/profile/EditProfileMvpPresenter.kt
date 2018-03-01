package brmobi.moop.ui.profile

import android.content.Intent
import android.net.Uri
import android.text.Editable
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 25/02/2018.
 */
interface EditProfileMvpPresenter<V : EditProfileMvpView> : MvpPresenter<V> {
    fun onAddPhotoClick()
    fun onCameraSelected()
    fun onGallerySelected()
    fun onCameraIntentCreated(cameraIntent: Intent, requestedBy: Int)
    fun onCameraActivityReturn()
    fun onPhotoCropped(data: Intent)
    fun onPhotoFromGallerySelected(data: Uri?)
    fun onImgAvatarClick()
    fun onViewReady()
    fun onBtnConfirmClick(text: Editable)
}