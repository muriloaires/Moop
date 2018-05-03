package brmobi.moop.ui.profile

import android.content.Intent
import android.net.Uri
import brmobi.moop.ui.base.MvpView
import com.yalantis.ucrop.UCrop
import java.io.File

/**
 * Created by murilo aires on 25/02/2018.
 */
interface EditProfileMvpView : MvpView {
    fun createDialogAddPhoto()
    fun requestCameraPermision()
    fun requestWriteReadPermision()
    fun setUserName(currentUserName: String)
    fun showPlaceHolderAvatar()
    fun showAvatar(currentProfilePic: String)
    fun openCameraActivity(intent: Intent, requestedBy: Int)
    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri)
    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri)
    fun loadPhotoOnImageView(foto: File)
    fun finishWithOkResult()
}