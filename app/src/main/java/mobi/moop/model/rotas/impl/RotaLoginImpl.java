package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.io.File;

import mobi.moop.R;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.UsuarioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaUsuario;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 12/07/2017.
 */

public class RotaLoginImpl {

    public void login(final Context context, String email, String senha, String deviceToken, String deviceType, final String logadoCom, final RotaUsuario.LoginHandler handler) {
        Call<Usuario> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaUsuario.class).login(email, senha, deviceToken, deviceType, logadoCom);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    UsuarioRepository.I.saveUsuarioLogado(context, response.body());
                    handler.onLogin();
                } else if (response.code() == 403) {
                    handler.onLoginError(context.getString(R.string.dados_invalidos));
                } else if (response.code() == 404) {
                    handler.onUserNotFound(logadoCom);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                handler.onLoginError(context.getString(R.string.algo_errado_ocorreu));
            }
        });

    }

    public void registrar(final Context context, String nome, String email, String passsword, String avatarUrl, String loginType, String deviceToken, String deviceType, File imgAvatar, final RotaUsuario.RegistroHandler handler) {
        RequestBody nomeBody = RequestBody.create(MediaType.parse("multipart/form-data"), nome);
        RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("multipart/form-data"), passsword);
        RequestBody loginTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), loginType);
        RequestBody deviceTokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), deviceToken);
        RequestBody deviceTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), deviceType);
        RequestBody avatarUrlBody = null;
        if (avatarUrl != null) {
            avatarUrlBody = RequestBody.create(MediaType.parse("multipart/form-data"), avatarUrl);
        }
        MultipartBody.Part body = null;
        if (imgAvatar != null) {
            body = MultipartBody.Part.createFormData("avatar", imgAvatar.getName(), RequestBody.create(MediaType.parse("image/*"), imgAvatar));
        }
        Call<Usuario> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaUsuario.class).registrar(nomeBody, emailBody, passwordBody, deviceTokenBody, deviceTypeBody, loginTypeBody, avatarUrlBody, body);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    UsuarioRepository.I.saveUsuarioLogado(context, response.body());
                    handler.onUserRegistred();
                } else if (response.code() == 302) {
                    handler.onRegistrationFail(context.getString(R.string.email_ja_cadastrado));
                } else if (response.code() == 401) {
                    handler.onRegistrationFail(context.getString(R.string.dados_invalidos));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                handler.onRegistrationFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
