package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.Chamado
import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 21/02/2018.
 */
interface TicketsEndpoints {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("chamado/{condominioId}/enviar.json")
    @Multipart
    fun postTicket(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long?,
                   @Part("titulo") titulo: RequestBody, @Part("texto") texto: RequestBody,
                   @Part body: MultipartBody.Part?): Observable<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("chamado/{condominioId}/me.json")
    fun getTickets(@Header("apiToken") apiToken: String,
                   @Path("condominioId") condominioSelecionadoId: Long?): Observable<GenericListResponse<Chamado>>

}