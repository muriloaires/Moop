package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class RotaFeedImpl {
    private Call<GenericListResponse<FeedItem>> call;

    public void getFeed(final Context context, Long condominioId, Integer limit, Integer offset, final RotaFeed.FeedHandler handler) {
        call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaFeed.class).getFeed(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), condominioId, limit, offset);
        call.enqueue(new Callback<GenericListResponse<FeedItem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<FeedItem>> call, Response<GenericListResponse<FeedItem>> response) {
                if (response.isSuccessful()) {
                    handler.onFeedReceived(response.body().getData());
                } else {
                    handler.onFeedReceiveFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<FeedItem>> call, Throwable t) {

            }
        });
    }
}
