package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.entities.Mensagem;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaMensagem;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class RotaMensagemImpl {

    public void postMensagem(final Context context, Usuario usuarioLogado, Long usuarioDestinoId, String mensagem, final RotaMensagem.MensagemHandler handler) {
        Call<Mensagem> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).postMensagem(usuarioLogado.getApiToken(), usuarioDestinoId, CondominioRepository.I.getCondominio(context).getId(), mensagem);
        call.enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful()) {
                    handler.onMensagemEnviada(response.body());
                } else {
                    handler.onEnvioMensagemError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                handler.onEnvioMensagemError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getMensagens(final Context context, Usuario usuarioLogado, Long usuarioDestinoId, final RotaMensagem.MensagemHandler handler) {
        Call<GenericListResponse<Mensagem>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).getMensagens(usuarioLogado.getApiToken(), usuarioDestinoId, CondominioRepository.I.getCondominio(context).getId());
        call.enqueue(new Callback<GenericListResponse<Mensagem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Mensagem>> call, Response<GenericListResponse<Mensagem>> response) {
                if (response.isSuccessful()) {
                    handler.onMensagensRecebidas(response.body().getData());
                } else {
                    handler.onEnvioMensagemError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Mensagem>> call, Throwable t) {
                handler.onEnvioMensagemError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getUltimasMensagens(final Context context, Usuario usuarioLogado, final RotaMensagem.UltimasMensagensHandler handler) {
        Call<GenericListResponse<Mensagem>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).getUltimasMensagensUsuario(usuarioLogado.getApiToken(), CondominioRepository.I.getCondominio(context).getId());
        call.enqueue(new Callback<GenericListResponse<Mensagem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Mensagem>> call, Response<GenericListResponse<Mensagem>> response) {
                if (response.isSuccessful()) {
                    handler.onUltimasMensagensRecebidas(response.body().getData());
                } else {
                    handler.onUltimasMensagensError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Mensagem>> call, Throwable t) {
                handler.onUltimasMensagensError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
