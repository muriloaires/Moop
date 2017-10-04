package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 30/09/2017.
 */

public interface RotaMoradores {

    public interface MoradoresHandler {
        void onMoradoresRecebidos(List<Usuario> body);

        void onRecebimentoMoradoresFail(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/moradores.json")
    Call<List<Usuario>> getMoradores(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Query("termo") String query);
}
