package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.Comentario;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 09/08/2017.
 */

public interface RotaComentarios {

    interface ComentariosHandler {
        void onComentarioEnviado(Comentario comentario);

        void onEnvioComentarioError(String error);

        void onComentariosRecebidos(List<Comentario> comentarios);

        void onRecebimentoComentariosError(String error);
    }


    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "comentario/{feedId}/postar.json")
    Call<Comentario> postComentario(@Header("apiToken") String apiToken, @Path("feedId") Long feedId, @Query("texto") String comentario);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "comentario/{feedId}/feed.json")
    Call<GenericListResponse<Comentario>> getComentarios(@Header("apiToken") String apiToken, @Path("feedId") Long feedId);


}
