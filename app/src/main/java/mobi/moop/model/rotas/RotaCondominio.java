package mobi.moop.model.rotas;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by murilo aires on 25/07/2017.
 */

public interface RotaCondominio {
    interface CondominiosHandler {
        void onCondominiosRecebidos(List condominios);

        void onGetCondominiosFail(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/condominios.json")
    Call<ResponseBody> getCondominiosUsuario(@Header("apiToken") String apiToken);
}
