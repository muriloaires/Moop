package brmobi.moop.features.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import brmobi.moop.model.entities.Usuario;
import brmobi.moop.model.rotas.RetrofitSingleton;
import brmobi.moop.model.rotas.RotaMoradores;
import brmobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AprovarService extends IntentService {

    public static final String ACTION_APROVAR = "aprovar";
    public static final String ACTION_DESAPROVAR = "desaprovar";
    public static final String PARAM_PERFIL_HAB_ID = "param_perfil_hab_id";

    public AprovarService() {
        super("AprovarService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_APROVAR.equals(action)) {
                final String perfilHabId = intent.getStringExtra(PARAM_PERFIL_HAB_ID);
                handleActionAprovar(true, perfilHabId);
            } else {
                final String perfilHabId = intent.getStringExtra(PARAM_PERFIL_HAB_ID);
                handleActionAprovar(false, perfilHabId);
            }
        }
    }



    private void handleActionAprovar(boolean aprovar, String perfilHabId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Integer.parseInt(perfilHabId));
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(this);
        Call<ResponseBody> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMoradores.class).aprovarMorador(usuario.getApiToken(), perfilHabId, aprovar);
        Response<ResponseBody> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                Log.d("SUCCESS", "SUCESSO");
            } else {
                Log.d("SUCCESS", "ERROR");
            }
        } catch (IOException e) {
            Log.d("SUCCESS", "ERROR");
        }

    }


}
