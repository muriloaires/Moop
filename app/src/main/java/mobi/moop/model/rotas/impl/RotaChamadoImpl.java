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
    public void postChamado(final Context context, String titulo, String descricao, File imgChamado, final RotaChamados.ChamadoHandler handler) {
        RequestBody tituloBody = RequestBody.create(MediaType.parse("multipart/form-data"), titulo);
        RequestBody descricaoBody = RequestBody.create(MediaType.parse("multipart/form-data"), descricao);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", imgChamado.getName(), RequestBody.create(MediaType.parse("image/*"), imgChamado));
        Call<ResponseBody> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaChamados.class).postChamado(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), CondominioRepository.I.getCondominio(context).getId(), tituloBody, descricaoBody, body);
        call.enqueue(new Callback<ResponseBody>() {
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
                handler.onCriarChamadoFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void loadChamados(final Context context, final RotaChamados.RecebimentoChamadoHandler handler) {
        String apiToken = UsuarioSingleton.I.getUsuarioLogado(context).getApiToken();
        Long condominioSelecionadoId = CondominioRepository.I.getCondominio(context).getId();
        Call<GenericListResponse<Chamado>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaChamados.class).loadChamados(apiToken, condominioSelecionadoId);
        call.enqueue(new Callback<GenericListResponse<Chamado>>() {
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
                handler.onRecebimentoFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
