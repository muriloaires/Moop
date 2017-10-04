package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.Chamado;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by murilo aires on 02/10/2017.
 */

public interface RotaChamados {


    interface ChamadoHandler {
        void onChamadoCriado(ResponseBody body);

        void onCriarChamadoFail(String errorBody);
    }

    interface RecebimentoChamadoHandler {

        void onChamadosRecebidos(List<Chamado> chamados);

        void onRecebimentoFail(String errorBody);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "chamado/{condominioId}/enviar.json")
    @Multipart
    Call<ResponseBody> postChamado(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Part("titulo") RequestBody titulo, @Part("texto") RequestBody texto, @Part MultipartBody.Part body);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "chamado/{condominioId}/me.json")
    Call<List<Chamado>> loadChamados(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioSelecionadoId);


}
