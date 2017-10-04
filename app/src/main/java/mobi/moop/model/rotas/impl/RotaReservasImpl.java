package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.entities.DisponibilidadeBem;
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.rotas.reponse.GenericResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class RotaReservasImpl {

    public void efetuarReserva(final Context context, Long disponibilidadeId, String data, final RotaReservas.ReservaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        Call<GenericResponse<ReservaBemComum>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).reservarDisponibilidade(usuario.getApiToken(), disponibilidadeId, data);
        call.enqueue(new Callback<GenericResponse<ReservaBemComum>>() {
            @Override
            public void onResponse(Call<GenericResponse<ReservaBemComum>> call, Response<GenericResponse<ReservaBemComum>> response) {
                if (response.isSuccessful()) {
                    handler.onReservaEfetuada(response.body().getData());
                } else {
                    handler.onError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<ReservaBemComum>> call, Throwable t) {
                handler.onError(context.getString(R.string.algo_errado_ocorreu));
            }
        });

    }

    public void getBensComund(final Context context, Long condominioId, final RotaReservas.BemComunHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        Call<GenericListResponse<BemComum>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getBensComuns(usuario.getApiToken(), condominioId);
        call.enqueue(new Callback<GenericListResponse<BemComum>>() {
            @Override
            public void onResponse(Call<GenericListResponse<BemComum>> call, Response<GenericListResponse<BemComum>> response) {
                if (response.isSuccessful()) {
                    handler.onBensComunsRecebidos(response.body().getData());
                } else {
                    handler.onRecebimentoBensComunsErro(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<BemComum>> call, Throwable t) {
                handler.onRecebimentoBensComunsErro(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getDisponibilidades(final Context context, Long bemComumId, final RotaReservas.DisponibilidadesHandler handler, String dataDesejada) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        Call<GenericListResponse<DisponibilidadeBem>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getDisponibilidadesBens(usuario.getApiToken(), bemComumId, dataDesejada);
        call.enqueue(new Callback<GenericListResponse<DisponibilidadeBem>>() {
            @Override
            public void onResponse(Call<GenericListResponse<DisponibilidadeBem>> call, Response<GenericListResponse<DisponibilidadeBem>> response) {
                if (response.isSuccessful()) {
                    handler.onDisponilidadesRecebidas(response.body().getData());
                } else {
                    handler.onRecebimentoDisponibilidadesErro(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<DisponibilidadeBem>> call, Throwable t) {
                handler.onRecebimentoDisponibilidadesErro(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }

    public void getReservas(final Context context, Long condominioId, final RotaReservas.ReservaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        Call<GenericListResponse<ReservaBemComum>> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getReservas(usuario.getApiToken(), condominioId);
        call.enqueue(new Callback<GenericListResponse<ReservaBemComum>>() {
            @Override
            public void onResponse(Call<GenericListResponse<ReservaBemComum>> call, Response<GenericListResponse<ReservaBemComum>> response) {
                if (response.isSuccessful()) {
                    handler.onReservasRecebidas(response.body().getData());
                } else {
                    handler.onRecebimentoReservasError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<ReservaBemComum>> call, Throwable t) {
                handler.onError(context.getString(R.string.algo_errado_ocorreu));
            }
        });
    }
}
