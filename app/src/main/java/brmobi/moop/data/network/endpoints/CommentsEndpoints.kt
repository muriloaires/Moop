package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.Comentario
import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 25/02/2018.
 */
interface CommentsEndpoints {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("comentario/{feedId}/postar.json")
    fun postComment(@Header("apiToken") apiToken: String, @Path("feedId") feedId: Long?, @Query("texto") comentario: String): Observable<Comentario>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("comentario/{feedId}/feed.json")
    fun getComments(@Header("apiToken") apiToken: String, @Path("feedId") feedId: Long?): Observable<GenericListResponse<Comentario>>


    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @DELETE("comentario/{comentarioId}/apagar.json")
    fun deleteComment(@Header("apiToken") apiToken: String, @Path("comentarioId") comentarioId: Long?): Observable<ResponseBody>
}