package brmobi.moop.model.rotas;

import java.util.List;

import brmobi.moop.model.entities.PerfilHabitacional;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 30/09/2017.
 */

public interface RotaMoradores {

    interface MoradoresHandler {
        void onMoradoresRecebidos(List<PerfilHabitacional> body);

        void onRecebimentoMoradoresFail(String error);
    }

    interface LiberarMoradoresHandler {
        void onMoradoresRecebidos(List<PerfilHabitacional> body);

        void onRecebimentoMoradoresFail(String error);

        void onPerfilAprovado(boolean aprovado, int adapterPosition);

        void onAprovacaoError(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/moradores.json")
    Call<List<PerfilHabitacional>> getMoradores(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Query("termo") String query);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{perfilHabitacionalId}/liberar.json")
    Call<ResponseBody> aprovarMorador(@Header("apiToken") String apiToken, @Path("perfilHabitacionalId") String perfilHabitacionalId, @Query("isLiberado") boolean liberado);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/moradores-nao-liberados.json")
    Call<List<PerfilHabitacional>> getMoradoresNaoLiberados(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);


}
