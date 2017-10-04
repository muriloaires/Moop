package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaMoradores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class RotaMoradoresImpl {

    public void getMoradores(Usuario usuario, Condominio condominio, final Context context, final RotaMoradores.MoradoresHandler handler, String query) {
        Call<List<Usuario>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMoradores.class).getMoradores(usuario.getApiToken(), condominio.getId(),query);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    handler.onMoradoresRecebidos(response.body());
                } else {
                    handler.onRecebimentoMoradoresFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                handler.onRecebimentoMoradoresFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

}
