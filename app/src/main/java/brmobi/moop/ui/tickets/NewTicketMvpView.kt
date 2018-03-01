package brmobi.moop.ui.tickets

import android.content.Intent
import android.net.Uri
import brmobi.moop.ui.base.MvpView
import com.yalantis.ucrop.UCrop
import java.io.File

/**
 * Created by murilo aires on 21/02/2018.
 */
interface NewTicketMvpView : MvpView {

    fun onInvalidTitle(resId: Int)

    fun onInvalidMessage(resId: Int)

    fun createDialogAddPhoto()

    fun requestCameraPermision()

    fun requestWriteReadPermision()

    fun openCameraActivity(intent: Intent, requestedBy: Int)

    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri)

    fun loadPhotoOnImageView(foto: File)

    fun openCropActivity(uCropOptions: UCrop.Options, photoUri: Uri?, destination: Uri)

    fun onTicketCreated()

}