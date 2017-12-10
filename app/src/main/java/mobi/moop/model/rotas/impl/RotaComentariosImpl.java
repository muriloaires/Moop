package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.util.ArrayList;

import mobi.moop.R;
import mobi.moop.features.comentarios.ComentariosActivity;
import mobi.moop.model.entities.Comentario;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaComentarios;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 09/08/2017.
 */

public class RotaComentariosImpl {
    private Call<Comentario> callPostComentario;
    private Call<GenericListResponse<Comentario>> callLoadComentarios;
    private Call<ResponseBody> callApagarComentarios;

    public void postComentario(final Context context, Long feedId, String texto, final RotaComentarios.ComentariosHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callPostComentario = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaComentarios.class).postComentario(usuario.getApiToken(), feedId, texto);
        callPostComentario.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if (response.isSuccessful()) {
                    handler.onComentarioEnviado(response.body());
                } else {
                    handler.onEnvioComentarioError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onEnvioComentarioError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelPostComentarioRequisition() {
        try {
            callPostComentario.cancel();
        } catch (Exception e) {
        }
    }

    public void loadComentarios(final Context context, long feedId, final RotaComentarios.ComentariosHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callLoadComentarios = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaComentarios.class).getComentarios(usuario.getApiToken(), feedId);
        callLoadComentarios.enqueue(new Callback<GenericListResponse<Comentario>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Comentario>> call, Response<GenericListResponse<Comentario>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 204) {
                        handler.onComentariosRecebidos(new ArrayList<Comentario>());
                    } else {
                        handler.onComentariosRecebidos(response.body().getData());
                    }
                } else {
                    handler.onRecebimentoComentariosError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Comentario>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onRecebimentoComentariosError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelLoadComentariosRequisition() {
        try {
            callLoadComentarios.cancel();
        } catch (Exception e) {
        }
    }


    public void apagarComentario(final Context context, final Comentario comentario, final RotaComentarios.ComentariosHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callApagarComentarios = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaComentarios.class).apagarComentario(usuario.getApiToken(), comentario.getId());
        callApagarComentarios.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onComentarioApagado(comentario);
                } else {
                    handler.onRecebimentoComentariosError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onRecebimentoComentariosError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }
}
