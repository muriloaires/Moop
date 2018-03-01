package brmobi.moop.data.media

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import br.com.airescovit.clim.di.ApplicationContext
import brmobi.moop.R
import brmobi.moop.utils.FotoUtils
import com.yalantis.ucrop.UCrop
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 21/02/2018.
 */
@Singleton
class AppMediaHelper @Inject constructor(@ApplicationContext val context: Context) : MediaHelper {

    override fun getOutputPhotFile(): File? {
        return FotoUtils.getOutputMediaFile()
    }

    override fun getUriForFile(photoFile: File): Uri? {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".my.package.name.provider", photoFile)
        } else {
            return Uri.fromFile(photoFile)
        }
    }

    override fun getDefaultUCropOptions(): UCrop.Options {
        val options = UCrop.Options()
        options.setFreeStyleCropEnabled(true)
        options.setHideBottomControls(true)
        options.setToolbarColor(context.resources.getColor(R.color.colorPrimary))
        options.setStatusBarColor(context.resources.getColor(R.color.colorPrimaryDark))
        options.setToolbarTitle(context.getString(R.string.cortar))
        options.setMaxBitmapSize(1920)
        options.withMaxResultSize(1920, 1920)
        return options
    }

    override fun getUriFromFilePath(path: String): Uri {
        return Uri.fromFile(File(path))
    }

    override fun getUriFromFile(file: File): Uri {
        return Uri.fromFile(file)
    }

    override fun getPathFromUri(uri: Uri?): String {
        return FotoUtils.getPath(context, uri)
    }

    override fun sendPictureBroadcast(path: String) {
        FotoUtils.sendPictureBroadcast(path, context)
    }

    override fun getUriFromNewFile(): Uri {
        return Uri.fromFile(FotoUtils.getOutputMediaFile())
    }
}