package brmobi.moop.ui.publication

import android.content.Intent
import android.net.Uri
import brmobi.moop.ui.base.MvpView
import com.yalantis.ucrop.UCrop
import java.io.File

/**
 * Created by murilo aires on 25/02/2018.
 */
interface NewPostMvpView : MvpView {
    fun createDialogAddPhoto()
    fun requestCameraPermision()
    fun requestWriteReadPermision()
    fun openCameraActivity(intent: Intent, requestedBy: Int)
    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri)
    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri)
    fun loadPhotoOnImageView(foto: File)
    fun finishWithOkResult()
    fun hideImgPost()
    fun hideBtnDeletePhoto()
    fun showBtnSelectPic()
}