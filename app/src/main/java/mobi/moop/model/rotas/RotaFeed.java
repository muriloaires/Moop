package mobi.moop.model.rotas;

import java.util.List;

import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 27/07/2017.
 */

public interface RotaFeed {

    interface FeedHandler {
        void onFeedReceived(List<FeedItem> items);

        void onFeedReceiveFail(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "feed/{condominioId}/feeds.json")
    Call<GenericListResponse<FeedItem>> getFeed(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Query("limit") Integer limit, @Query("offset") Integer offset);
}
