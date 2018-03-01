package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.GenericListResponse
import brmobi.moop.data.network.model.Mensagem
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 21/02/2018.
 */
interface MessagesEndpoint {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("mensagem/para/{usuarioId}/no-condominio/{condominioId}/enviar.json")
    fun postMessage(@Header("apiToken") apiToken: String, @Path("usuarioId") usuarioId: Long?,
                    @Path("condominioId") condominioId: Long?,
                    @Query("texto") mensagem: String): Observable<Mensagem>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("mensagem/com/{usuarioDestinoId}/no-condominio/{condominioId}/lista.json")
    fun getMessages(@Header("apiToken") apiToken: String, @Path("usuarioDestinoId") usuarioDestinoId: Long?,
                    @Path("condominioId") condominioId: Long?): Observable<GenericListResponse<Mensagem>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("mensagem/no-condominio/{condominioId}/ultimas.json")
    fun getUserLastMessages(@Header("apiToken") apiToken: String,
                            @Path("condominioId") condominioId: Long?): Observable<GenericListResponse<Mensagem>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @DELETE("mensagem/{mensagemId}/apagar.json")
    fun deleteMessage(@Header("apiToken") apiToken: String,
                      @Path("mensagemId") mensagemId: Long?): Observable<ResponseBody>
}