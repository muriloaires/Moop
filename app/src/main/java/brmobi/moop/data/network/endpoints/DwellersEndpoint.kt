package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.PerfilHabitacional
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by murilo aires on 21/02/2018.
 */
interface DwellersEndpoint {
    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{condominioId}/moradores.json")
    fun getDwellers(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long?,
                    @Query("termo") query: String): Observable<List<PerfilHabitacional>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio/{perfilHabitacionalId}/liberar.json")
    fun approveDweller(@Header("apiToken") apiToken: String,
                       @Path("perfilHabitacionalId") perfilHabitacionalId: Long,
                       @Query("isLiberado") liberado: Boolean): Observable<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio/{perfilHabitacionalId}/liberar.json")
    fun approveDwellerCall(@Header("apiToken") apiToken: String,
                           @Path("perfilHabitacionalId") perfilHabitacionalId: Long,
                           @Query("isLiberado") liberado: Boolean): Call<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{condominioId}/moradores-nao-liberados.json")
    fun getBlockedDwellers(@Header("apiToken") apiToken: String,
                           @Path("condominioId") condominioId: Long?): Observable<List<PerfilHabitacional>>


}