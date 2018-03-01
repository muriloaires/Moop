package brmobi.moop.data.network.endpoints

import brmobi.moop.data.network.model.Senha
import brmobi.moop.data.network.model.Usuario
import brmobi.moop.utils.AppConstants
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by murilo aires on 20/02/2018.
 */
interface UserEndpoints {

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("users/register_phone.json")
    @Multipart
    fun register(@Part("nome") nome: RequestBody, @Part("email") email: RequestBody, @Part("telefone") telefone: RequestBody, @Part("deviceToken") deviceToken: RequestBody, @Part("deviceType") deviceType: RequestBody, @Part("avatarUrl") avatarUrl: RequestBody?, @Part body: MultipartBody.Part?): Observable<Usuario>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @GET("users/retrieve_phone.json")
    fun login(@Query("telefone") numero: String, @Query("deviceToken") deviceToken: String, @Query("deviceType") deviceType: String): Observable<Usuario>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("users/update.json")
    @Multipart
    fun update(@Header("apiToken") apiToken: String, @Part("nome") nome: RequestBody, @Part body: MultipartBody.Part?): Observable<Usuario>

    @Headers("appToken:" + AppConstants.APP_TOKEN)
    @POST("users/gera-senha/nova.json")
    fun gerarNovaSenha(@Header("apiToken") apiToken: String): Observable<Senha>
}