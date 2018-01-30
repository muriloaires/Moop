package brmobi.moop.model.rotas;

import java.util.List;

import brmobi.moop.model.entities.Notificacao;
import brmobi.moop.model.rotas.reponse.GenericListResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by murilo aires on 20/12/2017.
 */

public interface RotaNotificacoes {

    interface NotificacoesHandler {

        void onNotificacoesReceived(List<Notificacao> body);

        void onError(String errorBody);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "notificacao/{condominioId}/todas.json")
    Call<GenericListResponse<Notificacao>> getNotificacoes(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);
}
