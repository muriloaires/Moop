package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.data.network.model.Notificacao
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by murilo aires on 22/02/2018.
 */
interface NotificationsEndpoints {
    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("notificacao/{condominioId}/todas.json")
    fun getNotifications(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long?): Observable<GenericListResponse<Notificacao>>
}