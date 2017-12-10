package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.Mensagem;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 30/09/2017.
 */

public interface RotaMensagem {


    interface MensagemHandler {
        void onMensagemEnviada(Mensagem mensagem);

        void onEnvioMensagemError(String error);

        void onMensagensRecebidas(List<Mensagem> mensagens);

        void onRecebimentoMensagensError(String error);

        void onApagarMensagemError(String error);

        void onMensagemApagada(Mensagem mensagem);
    }

    interface UltimasMensagensHandler {
        void onUltimasMensagensRecebidas(List<Mensagem> ultimasMensagens);

        void onUltimasMensagensError(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "mensagem/para/{usuarioId}/no-condominio/{condominioId}/enviar.json")
    Call<Mensagem> postMensagem(@Header("apiToken") String apiToken, @Path("usuarioId") Long usuarioId, @Path("condominioId") Long condominioId, @Query("texto") String mensagem);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "mensagem/com/{usuarioDestinoId}/no-condominio/{condominioId}/lista.json")
    Call<GenericListResponse<Mensagem>> getMensagens(@Header("apiToken") String apiToken, @Path("usuarioDestinoId") Long usuarioDestinoId, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "mensagem/no-condominio/{condominioId}/ultimas.json")
    Call<GenericListResponse<Mensagem>> getUltimasMensagensUsuario(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @DELETE(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "mensagem/{mensagemId}/apagar.json")
    Call<ResponseBody> apagarMensagem(@Header("apiToken") String apiToken, @Path("mensagemId") Long mensagemId);


}
