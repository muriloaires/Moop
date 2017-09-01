package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.entities.Bloco;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaCondominio;
import mobi.moop.model.rotas.reponse.GenericListResponse;
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
        Call<GenericListResponse<Condominio>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getCondominiosUsuario(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken());
        call.enqueue(new Callback<GenericListResponse<Condominio>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Condominio>> call, Response<GenericListResponse<Condominio>> response) {
                if (response.isSuccessful()) {
                    CondominioRepository.I.saveCondominio(context, response.body().getData());
                    handler.onCondominiosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Condominio>> call, Throwable t) {
                handler.onGetCondominiosFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getCondominiosCep(final Context context, String cep, final RotaCondominio.CondominiosHandler handler) {
        Call<GenericListResponse<Condominio>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getCondominiosCEP(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), cep);
        call.enqueue(new Callback<GenericListResponse<Condominio>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Condominio>> call, Response<GenericListResponse<Condominio>> response) {
                if (response.isSuccessful()) {
                    handler.onCondominiosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Condominio>> call, Throwable t) {
                handler.onGetCondominiosFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getBlocosCondominio(final Context context, Long condominioId, final RotaCondominio.BlocosHandler handler) {
        Call<GenericListResponse<Bloco>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getBlocosCondominio(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), condominioId);
        call.enqueue(new Callback<GenericListResponse<Bloco>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Bloco>> call, Response<GenericListResponse<Bloco>> response) {
                if (response.isSuccessful()) {
                    handler.onBlocosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Bloco>> call, Throwable t) {
                handler.onGetBlocosFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getUnidadeBlocos(final Context context, Long condominioId, Long blocoId, final RotaCondominio.UnidadesHandler handler) {
        Call<GenericListResponse<Unidade>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getUnidadesBloco(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), blocoId, condominioId);
        call.enqueue(new Callback<GenericListResponse<Unidade>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Unidade>> call, Response<GenericListResponse<Unidade>> response) {
                if (response.isSuccessful()) {
                    handler.onUnidadesRecebidas(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Unidade>> call, Throwable t) {
                handler.onGetUnidadesError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void registrarUnidade(final Context context, Long blocoId, boolean isProprietario, boolean isMorador, String unidade, final RotaCondominio.RegistroUnidadeHandler handler) {
        Call<ResponseBody> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).registrarEmUnidade(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), blocoId, isProprietario, isMorador, unidade);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onPerfilRegistrado();
                } else {
                    handler.onRegistrationFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                handler.onRegistrationFail(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
