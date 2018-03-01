package brmobi.moop.ui.publication

import android.content.Intent
import android.net.Uri
import brmobi.moop.ui.base.MvpPresenter

/**
 * Created by murilo aires on 25/02/2018.
 */
interface NewPostMvpPresenter<V : NewPostMvpView> : MvpPresenter<V> {
    fun onAddPhotoClick()
    fun onCameraSelected()
    fun onGallerySelected()
    fun onCameraIntentCreated(cameraIntent: Intent, requestedBy: Int)
    fun onCameraActivityReturn()
    fun onPhotoCropped(data: Intent)
    fun onPhotoFromGallerySelected(data: Uri?)
    fun onImgAvatarClick()
    fun onRemoveImageClick()
    fun onBtnChooseImageClick()
    fun onPublishMenuClick(text: String)
    fun removePic()
}