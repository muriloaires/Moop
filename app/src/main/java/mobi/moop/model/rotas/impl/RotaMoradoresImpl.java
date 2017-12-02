package mobi.moop.model.rotas.impl;

import android.content.Context;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.PerfilHabitacional;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaMoradores;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class RotaMoradoresImpl {

    private Call<List<PerfilHabitacional>> callGetMoradores;
    private Call<List<PerfilHabitacional>> callGetMoradoresNaoLiberados;
    private Call<ResponseBody> callAprovarMorador;

    public void getMoradores(Usuario usuario, Condominio condominio, final Context context, final RotaMoradores.MoradoresHandler handler, String query) {
        callGetMoradores = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMoradores.class).getMoradores(usuario.getApiToken(), condominio.getId(), query);
        callGetMoradores.enqueue(new Callback<List<PerfilHabitacional>>() {
            @Override
            public void onResponse(Call<List<PerfilHabitacional>> call, Response<List<PerfilHabitacional>> response) {
                if (response.isSuccessful()) {
                    handler.onMoradoresRecebidos(response.body());
                } else {
                    handler.onRecebimentoMoradoresFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<List<PerfilHabitacional>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onRecebimentoMoradoresFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetMoradoresRequisition() {
        try {
            callGetMoradores.cancel();
        } catch (Exception e) {
        }
    }

    public void getMoradoresNaoLiberados(final Context context, final RotaMoradores.LiberarMoradoresHandler handler) {

        callGetMoradoresNaoLiberados = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMoradores.class).getMoradoresNaoLiberados(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), CondominioRepository.I.getCondominio(context).getId());
        callGetMoradoresNaoLiberados.enqueue(new Callback<List<PerfilHabitacional>>() {
            @Override
            public void onResponse(Call<List<PerfilHabitacional>> call, Response<List<PerfilHabitacional>> response) {
                if (response.isSuccessful()) {
                    handler.onMoradoresRecebidos(response.body());
                } else {
                    handler.onRecebimentoMoradoresFail(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<List<PerfilHabitacional>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onRecebimentoMoradoresFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetMoradoresAprovarRequisition() {
        try {
            callGetMoradoresNaoLiberados.cancel();
        } catch (Exception e) {
        }
    }

    public void aceitarMorador(final Context context, Long perfilHabitacinalId, final int adapterPosition, final boolean isLiberado, final RotaMoradores.LiberarMoradoresHandler handler) {
        callAprovarMorador = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaMoradores.class).aprovarMorador(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), perfilHabitacinalId, isLiberado);
        callAprovarMorador.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onPerfilAprovado(isLiberado, adapterPosition);
                } else {
                    handler.onAprovacaoError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onAprovacaoError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });

    }

    public void cancelAprovarMoradorRequisition() {
        try {
            callAprovarMorador.cancel();
        } catch (Exception e) {
        }
    }
}
