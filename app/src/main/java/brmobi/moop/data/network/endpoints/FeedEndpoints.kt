package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.data.network.model.FeedItem
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 21/02/2018.
 */
interface FeedEndpoints {
    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("feed/{condominioId}/feeds.json")
    fun getFeed(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long, @Query("limit") limit: Int, @Query("offset") offset: Int): Observable<GenericListResponse<FeedItem>>


    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("feed/{condominioId}/condominio.json")
    @Multipart
    fun postFeed(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long, @Part("titulo") titulo: RequestBody?, @Part("texto") texto: RequestBody?, @Part imagem: MultipartBody.Part?): Observable<FeedItem>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("feed/{feedId}/curtir.json")
    fun likeFeedItem(@Header("apiToken") apiToken: String, @Path("feedId") feedId: Long): Observable<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("feed/{feedId}/descurtir.json")
    fun unlikeFeedItem(@Header("apiToken") apiToken: String, @Path("feedId") feedId: Long): Observable<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @DELETE("feed/{feedId}/apagar.json")
    fun deleteFeed(@Header("apiToken") apiToken: String, @Path("feedId") feedId: Long): Observable<ResponseBody>
}