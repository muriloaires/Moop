package mobi.moop.model.rotas.impl;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;

import mobi.moop.R;
import mobi.moop.features.feed.FeedFragment;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class RotaFeedImpl {
    private Call<GenericListResponse<FeedItem>> callGetFeed;
    private Call<FeedItem> callPostFeed;
    private Call<ResponseBody> callCurtirFeed;
    private Call<ResponseBody> callApagarFeed;

    public void getFeed(final Context context, Long condominioId, Integer limit, final Integer offset, final RotaFeed.FeedHandler handler) {
        callGetFeed = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).getFeed(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), condominioId, limit, offset);
        callGetFeed.enqueue(new Callback<GenericListResponse<FeedItem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<FeedItem>> call, Response<GenericListResponse<FeedItem>> response) {
                if (response.isSuccessful()) {
                    handler.onFeedReceived(response.body().getData(), offset);
                } else if (response.code() == 403) {
                    handler.onUsuarioNaoLiberado();
                } else {
                    handler.onFeedReceiveFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<FeedItem>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onFeedReceiveFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetFeedRequisition() {
        try {
            callGetFeed.cancel();
        } catch (Exception e) {

        }

    }

    public void publish(final Context context, Usuario usuario, Long condominioId, String texto, File imgPost, final RotaFeed.FeedPublishHandler handler) {
        RequestBody titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), "d");
        RequestBody textBody = null;
        if (texto != null && !texto.equals("")) {
            textBody = RequestBody.create(MediaType.parse("multipart/form-data"), texto);
        }
        MultipartBody.Part body = null;
        if (imgPost != null) {
            body = MultipartBody.Part.createFormData("imagem", imgPost.getName(), RequestBody.create(MediaType.parse("image/*"), imgPost));
        }

        callPostFeed = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).postFeed(usuario.getApiToken(), condominioId, titleBody, textBody, body);
        callPostFeed.enqueue(new Callback<FeedItem>() {
            @Override
            public void onResponse(Call<FeedItem> call, Response<FeedItem> response) {
                if (response.isSuccessful()) {
                    handler.onFeedPublised(response.body());
                } else {
                    handler.onFeedPublishFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<FeedItem> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onFeedPublishFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelPostFeedRequisition() {
        try {
            callPostFeed.cancel();
        } catch (Exception e) {
        }
    }

    public void curtirFeed(final Context context, final Long feedId, final RotaFeed.CurtidaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callCurtirFeed = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).curtirFeed(usuario.getApiToken(), feedId);
        callCurtirFeed.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonRespostaStr = response.body().string();
                        JSONObject jsonResposta = new JSONObject(jsonRespostaStr);
                        Integer curtidas = jsonResposta.getInt("curtidas");
                        handler.onFeedCurtido(feedId, curtidas);
                    } catch (Exception e) {
                        handler.onCurtirFeedFail(feedId, context.getString(R.string.algo_errado_ocorreu));
                    }

                } else {
                    handler.onCurtirFeedFail(feedId, RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onCurtirFeedFail(feedId, context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelarCurtidaRequisition() {
        try {
            callCurtirFeed.cancel();
        } catch (Exception e) {

        }
    }

    public void descurtirFeed(final Context context, final Long feedId, final RotaFeed.CurtidaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callCurtirFeed = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).descurtirFeed(usuario.getApiToken(), feedId);
        callCurtirFeed.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onFeedDescurtido(feedId);
                } else {
                    handler.onDescurtirFeedError(feedId, RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onCurtirFeedFail(feedId, context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void apagarFeed(final Context context, final FeedItem feedItem, final RotaFeed.FeedHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callApagarFeed = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).apagarFeed(usuario.getApiToken(), feedItem.getId());
        callApagarFeed.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onFeedApagado(feedItem);
                } else {
                    handler.onApagarFeedError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onApagarFeedError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }
}
