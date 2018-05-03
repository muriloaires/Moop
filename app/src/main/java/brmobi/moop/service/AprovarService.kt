package brmobi.moop.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import brmobi.moop.MoopApplication
import brmobi.moop.data.DataManager
import brmobi.moop.di.component.DaggerServiceComponent
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AprovarService : IntentService("AprovarService") {

    @Inject
    lateinit var dataManager: DataManager


    override fun onCreate() {
        super.onCreate()
        val component = DaggerServiceComponent.builder()
                .applicationComponent((application as MoopApplication).mApplicationComponent)
                .build()
        component.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_APROVAR == action) {
                val perfilHabId = intent.getStringExtra(PARAM_PERFIL_HAB_ID)
                handleActionAprovar(true, perfilHabId)
            } else {
                val perfilHabId = intent.getStringExtra(PARAM_PERFIL_HAB_ID)
                handleActionAprovar(false, perfilHabId)
            }
        }
    }


    private fun handleActionAprovar(aprovar: Boolean, perfilHabId: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(Integer.parseInt(perfilHabId))
        val call = dataManager!!.approveDwellerCall(dataManager.getCurrentAccessToken(), java.lang.Long.parseLong(perfilHabId), aprovar)
        val response: Response<ResponseBody>? = null
        try {
            call.execute()
        } catch (ignored: IOException) {
        }

    }

    companion object {

        val ACTION_APROVAR = "aprovar"
        val ACTION_DESAPROVAR = "desaprovar"
        val PARAM_PERFIL_HAB_ID = "param_perfil_hab_id"
    }


}
