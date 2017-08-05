package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.io.File;

import mobi.moop.R;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class RotaFeedImpl {
    private Call<GenericListResponse<FeedItem>> call;

    public void getFeed(final Context context, Long condominioId, Integer limit, final Integer offset, final RotaFeed.FeedHandler handler) {
        call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).getFeed(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), condominioId, limit, offset);
        call.enqueue(new Callback<GenericListResponse<FeedItem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<FeedItem>> call, Response<GenericListResponse<FeedItem>> response) {
                if (response.isSuccessful()) {
                    handler.onFeedReceived(response.body().getData(), offset);
                } else {
                    handler.onFeedReceiveFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<FeedItem>> call, Throwable t) {
                handler.onFeedReceiveFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void publish(final Context context, Usuario usuario, Long condominioId, String texto, File imgPost, final RotaFeed.FeedPublishHandler handler) {
        RequestBody titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), "d");
        RequestBody textBody = null;
        if (!texto.equals("")) {
            textBody = RequestBody.create(MediaType.parse("multipart/form-data"), texto);
        }
        MultipartBody.Part body = null;
        if (imgPost != null) {
            body = MultipartBody.Part.createFormData("imagem", imgPost.getName(), RequestBody.create(MediaType.parse("image/*"), imgPost));
        }

        Call<FeedItem> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).postFeed(usuario.getApiToken(), condominioId, titleBody, textBody, body);
        call.enqueue(new Callback<FeedItem>() {
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
                handler.onFeedPublishFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
