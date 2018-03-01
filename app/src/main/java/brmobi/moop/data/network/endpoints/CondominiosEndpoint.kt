package brmobi.moop.data.network.endpoints

import brmobi.moop.data.db.model.Condominio
import brmobi.moop.data.network.model.*
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by murilo aires on 19/02/2018.
 */
interface CondominiosEndpoint {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/condominios.json")
    fun getUserCondominiuns(@Header("apiToken") apiToken: String): Observable<GenericListResponse<Condominio>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{cep}/condominios.json")
    fun getCondominiusFromZipCode(@Header("apiToken") apiToken: String,
                                  @Path("cep") cep: String): Observable<GenericListResponse<Condominio>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{condominioId}/blocos.json")
    fun getCondominiumBlocs(@Header("apiToken") apiToken: String,
                            @Path("condominioId") condominioId: Long?): Observable<GenericListResponse<Bloco>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{blocoId}/{condominioId}/unidades.json")
    fun getBlocUnits(@Header("apiToken") apiToken: String, @Path("blocoId") blocoId: Long?,
                     @Path("condominioId") condominioId: Long?): Observable<GenericListResponse<Unidade>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio/{bloco}/vincular/unidade.json")
    fun doRegisterInUnit(@Header("apiToken") apiToken: String, @Path("bloco") blocoId: Long?,
                         @Query("isProprietario") isProprietario: Boolean,
                         @Query("isMorador") isMorador: Boolean,
                         @Query("numero") unidade: String): Observable<ResponseBody>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio.json")
    fun doRegisterCondominium(@Header("apiToken") apiToken: String, @Query("cep") cep: String,
                              @Query("nome") nome: String, @Query("logradouro") logradouro: String,
                              @Query("numero") numero: String, @Query("telefone") telefone: String,
                              @Query("isHorizontal") horizontal: Boolean,
                              @Query("blocos") blocos: String): Observable<GenericResponse<CadastroCondominio>>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio/{condominioId}/bloco.json")
    fun doRegisterBloc(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long?,
                       @Query("nome") nome: String, @Query("numero") numero: String): Observable<ResponseBody>


    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("condominio/{condominioId}/detalhe.json")
    fun getCondominiumDetail(@Header("apiToken") apiToken: String,
                             @Path("condominioId") condominioId: Long?): Observable<Condominio>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("condominio/{condominioId}/desvincular.json")
    fun detachFromCondominium(@Header("apiToken") apiToken: String, @Path("condominioId") condominioId: Long?): Observable<ResponseBody>
}