package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.Bloco;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 25/07/2017.
 */

public interface RotaCondominio {

    interface CondominiosHandler {
        void onCondominiosRecebidos(List<Condominio> condominios);

        void onGetCondominiosFail(String error);
    }

    interface BlocosHandler {
        void onBlocosRecebidos(List<Bloco> blocos);

        void onGetBlocosFail(String error);
    }

    interface UnidadesHandler {
        void onUnidadesRecebidas(List<Unidade> unidades);

        void onGetUnidadesError(String error);
    }

    interface RegistroUnidadeHandler {
        void onPerfilRegistrado();

        void onRegistrationFail(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/condominios.json")
    Call<GenericListResponse<Condominio>> getCondominiosUsuario(@Header("apiToken") String apiToken);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{cep}/condominios.json")
    Call<GenericListResponse<Condominio>> getCondominiosCEP(@Header("apiToken") String apiToken, @Path("cep") String cep);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/blocos.json")
    Call<GenericListResponse<Bloco>> getBlocosCondominio(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{blocoId}/{condominioId}/unidades.json")
    Call<GenericListResponse<Unidade>> getUnidadesBloco(@Header("apiToken") String apiToken, @Path("blocoId") Long blocoId, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{unidadeId}/perfil.json")
    Call<ResponseBody> registrarEmUnidade(@Header("apiToken") String apiToken, @Path("unidadeId") Long unidadeId, @Query("isProprietario") boolean isProprietario, @Query("isMorador") boolean isMorador);

}
