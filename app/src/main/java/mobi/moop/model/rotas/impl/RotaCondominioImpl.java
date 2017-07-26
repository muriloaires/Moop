package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 25/07/2017.
 */

public class RotaCondominioImpl {
    public void getCondominiosUsuarioLogado(final Context context, final RotaCondominio.CondominiosHandler handler) {
        Call<ResponseBody> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getCondominiosUsuario(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onCondominiosRecebidos(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                handler.onGetCondominiosFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
