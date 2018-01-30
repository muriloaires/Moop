package brmobi.moop.model.rotas.impl;

import android.content.Context;

import brmobi.moop.R;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.entities.Notificacao;
import brmobi.moop.model.entities.Usuario;
import brmobi.moop.model.repository.CondominioRepository;
import brmobi.moop.model.rotas.RetrofitSingleton;
import brmobi.moop.model.rotas.RotaNotificacoes;
import brmobi.moop.model.rotas.reponse.GenericListResponse;
import brmobi.moop.model.singleton.UsuarioSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 20/12/2017.
 */

public class RotaNotificacoesImpl {
    public void getNotificacoes(final Context context, final RotaNotificacoes.NotificacoesHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        Condominio condominio = CondominioRepository.I.getCondominio(context);
        Call<GenericListResponse<Notificacao>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaNotificacoes.class).getNotificacoes(usuario.getApiToken(), condominio.getId());
        call.enqueue(new Callback<GenericListResponse<Notificacao>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Notificacao>> call, Response<GenericListResponse<Notificacao>> response) {
                if (response.isSuccessful()) {
                    handler.onNotificacoesReceived(response.body().getData());
                } else {
                    handler.onError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Notificacao>> call, Throwable t) {
                handler.onError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
