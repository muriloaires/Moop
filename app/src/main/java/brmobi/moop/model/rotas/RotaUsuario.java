package brmobi.moop.model.rotas;

import brmobi.moop.model.entities.Senha;
import brmobi.moop.model.entities.Usuario;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 24/07/2017.
 */

public interface RotaUsuario {

    interface LoginHandler {
        void onLogin();

        void onLoginError(String error);

        void onUserNotFound();
    }

    interface RegistroHandler {
        void onUserRegistred();

        void onRegistrationFail(String error);
    }

    interface UpdateHandler {
        void onUserUpdated();

        void onUpdateFail(String error);
    }

    interface GerarSenhaHandler {
        void onSenhaGerada(String senha);

        void onError(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "users/register_phone.json")
    @Multipart
    Call<Usuario> registrar(@Part("nome") RequestBody nome, @Part("email") RequestBody email, @Part("telefone") RequestBody telefone, @Part("deviceToken") RequestBody deviceToken, @Part("deviceType") RequestBody deviceType, @Part("avatarUrl") RequestBody avatarUrl, @Part MultipartBody.Part body);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "users/retrieve_phone.json")
    Call<Usuario> login(@Query("telefone") String numero, @Query("deviceToken") String deviceToken, @Query("deviceType") String deviceType);


    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "users/update.json")
    @Multipart
    Call<Usuario> update(@Header("apiToken") String apiToken, @Part("nome") RequestBody nome, @Part MultipartBody.Part body);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "users/gera-senha/nova.json")
    Call<Senha> gerarNovaSenha(@Header("apiToken") String apiToken);
}
