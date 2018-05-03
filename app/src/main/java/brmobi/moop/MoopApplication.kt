package brmobi.moop

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import brmobi.moop.data.DataManager
import brmobi.moop.di.component.ApplicationComponent
import brmobi.moop.di.component.DaggerApplicationComponent
import brmobi.moop.di.module.ApplicationModule
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

/**
 * Created by murilo aires on 12/07/2017.
 */

class MoopApplication : Application() {

    @Inject
    lateinit var mDataManager: DataManager

    lateinit var mApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        mApplicationComponent.inject(this)


        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        try {
            val info = packageManager.getPackageInfo("mobi.moop", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }


    companion object {
        var screenWidth: Int = 0
    }
}
