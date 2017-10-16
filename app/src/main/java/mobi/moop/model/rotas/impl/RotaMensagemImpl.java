package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.util.ArrayList;

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

    private Call<GenericListResponse<Mensagem>> callUltimasMensagens;
    private Call<GenericListResponse<Mensagem>> callGetMensagens;
    private Call<Mensagem> callPostMensagem;

    public void postMensagem(final Context context, Usuario usuarioLogado, Long usuarioDestinoId, String mensagem, final RotaMensagem.MensagemHandler handler) {
        callPostMensagem = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).postMensagem(usuarioLogado.getApiToken(), usuarioDestinoId, CondominioRepository.I.getCondominio(context).getId(), mensagem);
        callPostMensagem.enqueue(new Callback<Mensagem>() {
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
        callGetMensagens = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).getMensagens(usuarioLogado.getApiToken(), usuarioDestinoId, CondominioRepository.I.getCondominio(context).getId());
        callGetMensagens.enqueue(new Callback<GenericListResponse<Mensagem>>() {
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
                if (!call.isCanceled()) {
                    handler.onEnvioMensagemError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void getUltimasMensagens(final Context context, Usuario usuarioLogado, final RotaMensagem.UltimasMensagensHandler handler) {
        callUltimasMensagens = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMensagem.class).getUltimasMensagensUsuario(usuarioLogado.getApiToken(), CondominioRepository.I.getCondominio(context).getId());
        callUltimasMensagens.enqueue(new Callback<GenericListResponse<Mensagem>>() {
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
                if (!call.isCanceled()) {
                    handler.onUltimasMensagensError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelPostMensagemRequisition() {
        try {
            callPostMensagem.cancel();
        } catch (Exception e) {
        }
    }

    public void cancelGetMensagensRequisition() {
        try {
            callGetMensagens.cancel();
        } catch (Exception e) {
        }
    }

    public void cancelGetUltimasMensagensRequisition() {
        try {
            callUltimasMensagens.cancel();
        } catch (Exception e) {
        }
    }

}
