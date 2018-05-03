package brmobi.moop.data.media

import android.net.Uri
import com.yalantis.ucrop.UCrop
import java.io.File

/**
 * Created by murilo aires on 21/02/2018.
 */
interface MediaHelper {
    fun getOutputPhotFile(): File?

    fun getUriForFile(photoFile: File): Uri?

    fun getDefaultUCropOptions(): UCrop.Options

    fun getUriFromFilePath(path: String): Uri

    fun getUriFromFile(file: File): Uri

    fun getPathFromUri(uri: Uri?): String

    fun sendPictureBroadcast(path: String)

    fun getUriFromNewFile(): Uri
}