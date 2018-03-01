package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.*
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 22/02/2018.
 */
interface ReservationsEndpoints {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("reserva/condominio/{condominioId}/bens.json")
    fun getSharedResources(@Header("apiToken") apiToken: String,
                           @Path("condominioId") condominioId: Long?)
            : Observable<GenericListResponse<BemComum>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("reserva/bem/{bemId}/disponibilidades.json")
    fun getResourceAvailability(@Header("apiToken") apiToken: String, @Path("bemId") bemId: Long?
                                , @Query("dataDesejada") dataDesejada: String)
            : Observable<GenericListResponse<DisponibilidadeBem>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("reserva/{disponibilidadeId}/reservar.json")
    fun reserveAvailability(@Header("apiToken") apiToken: String,
                            @Path("disponibilidadeId") disponibilidadeId: Long?, @Query("dataDesejada") dataDesejada: String)
            : Observable<GenericResponse<ReservaBemComum>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("reserva/{condominioId}/reservas.json")
    fun getReservations(@Header("apiToken") apiToken: String, @Path("condominioId") bemId: Long?)
            : Observable<GenericListResponse<ReservaBemComum>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("reserva/{condominioId}/{bemId}/dias.json")
    fun getSharedResourcesDays(@Header("apiToken") apiToken: String,
                               @Path("condominioId") condominioId: Long?,
                               @Path("bemId") bemId: Long?, @Query("mes") mes: Int?,
                               @Query("ano") ano: Int?): Observable<GenericListResponse<DiaBemComum>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("reserva/{reservaId}/cancelar.json")
    fun cancelReservation(@Header("apiToken") apiToken: String,
                          @Path("reservaId") reservaId: Long?): Observable<ResponseBody>
}