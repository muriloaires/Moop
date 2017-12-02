package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.io.File;
import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Chamado;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaChamados;
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
 * Created by murilo aires on 02/10/2017.
 */

public class RotaChamadoImpl {
    private Call<ResponseBody> callPostChamado;
    private Call<GenericListResponse<Chamado>> callLoadChamados;

    public void postChamado(final Context context, String titulo, String descricao, File imgChamado, final RotaChamados.ChamadoHandler handler) {
        RequestBody tituloBody = RequestBody.create(MediaType.parse("multipart/form-data"), titulo);
        RequestBody descricaoBody = RequestBody.create(MediaType.parse("multipart/form-data"), descricao);
        MultipartBody.Part body;
        if (imgChamado != null) {
            body = MultipartBody.Part.createFormData("avatar", imgChamado.getName(), RequestBody.create(MediaType.parse("image/*"), imgChamado));
        }
        callPostChamado = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaChamados.class).postChamado(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), CondominioRepository.I.getCondominio(context).getId(), tituloBody, descricaoBody, body);
        callPostChamado.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onChamadoCriado(response.body());
                } else {
                    handler.onCriarChamadoFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onCriarChamadoFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelPostChamadoRequisition() {
        try {
            callPostChamado.cancel();
        } catch (Exception e) {

        }
    }

    public void loadChamados(final Context context, final RotaChamados.RecebimentoChamadoHandler handler) {
        String apiToken = UsuarioSingleton.I.getUsuarioLogado(context).getApiToken();
        Long condominioSelecionadoId = CondominioRepository.I.getCondominio(context).getId();
        callLoadChamados = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaChamados.class).loadChamados(apiToken, condominioSelecionadoId);
        callLoadChamados.enqueue(new Callback<GenericListResponse<Chamado>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Chamado>> call, Response<GenericListResponse<Chamado>> response) {
                if (response.isSuccessful()) {
                    handler.onChamadosRecebidos(response.body().getData());
                } else {
                    handler.onRecebimentoFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Chamado>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onRecebimentoFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelLoadChamadosRequisition() {
        try {
            callLoadChamados.cancel();
        } catch (Exception e) {

        }
    }
}
