package brmobi.moop.model.rotas;

import java.util.List;

import brmobi.moop.model.entities.Bloco;
import brmobi.moop.model.entities.CadastroCondominio;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.entities.Unidade;
import brmobi.moop.model.rotas.reponse.GenericListResponse;
import brmobi.moop.model.rotas.reponse.GenericResponse;
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

    interface CadastroCondominioHandler {
        void onCondominioCadastrado(CadastroCondominio body);

        void onCadastroError(String error);
    }

    interface AddBlocoHandler {
        void onBlocoAdicionado(ResponseBody body);

        void onBlocoAddError(String error);
    }

    interface DetalheCondominioHandler {
        void onDetalheRecebido(Condominio condominio);

        void onError(String error);
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
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{bloco}/vincular/unidade.json")
    Call<ResponseBody> registrarEmUnidade(@Header("apiToken") String apiToken, @Path("bloco") Long blocoId, @Query("isProprietario") boolean isProprietario, @Query("isMorador") boolean isMorador, @Query("numero") String unidade);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio.json")
    Call<GenericResponse<CadastroCondominio>> cadastrarCondominio(@Header("apiToken") String apiToken, @Query("cep") String cep, @Query("nome") String nome, @Query("logradouro") String logradouro, @Query("numero") String numero, @Query("telefone") String telefone, @Query("isHorizontal") boolean horizontal, @Query("blocos") String blocos);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/bloco.json")
    Call<ResponseBody> cadastrarBloco(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Query("nome") String nome, @Query("numero") String numero);


    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/detalhe.json")
    Call<Condominio> getDetalheCondominio(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "condominio/{condominioId}/desvincular.json")
    Call<ResponseBody> desvincularCondominio(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);
}
