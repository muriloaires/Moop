package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 27/07/2017.
 */

public interface RotaFeed {

    interface FeedHandler {
        void onFeedReceived(List<FeedItem> items, int offset);

        void onFeedReceiveFail(String error);
    }

    interface FeedPublishHandler {
        void onFeedPublised(FeedItem feedItem);

        void onFeedPublishFail(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "feed/{condominioId}/feeds.json")
    Call<GenericListResponse<FeedItem>> getFeed(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Query("limit") Integer limit, @Query("offset") Integer offset);


    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "feed/{condominioId}/condominio.json")
    @Multipart
    Call<FeedItem> postFeed(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Part("titulo") RequestBody titulo, @Part("texto") RequestBody texto, @Part MultipartBody.Part imagem);
}
