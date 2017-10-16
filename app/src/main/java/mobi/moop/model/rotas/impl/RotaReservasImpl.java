package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.entities.BemComum;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.DiaBemComum;
import mobi.moop.model.entities.DisponibilidadeBem;
import mobi.moop.model.entities.ReservaBemComum;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.CondominioRepository;
import mobi.moop.model.rotas.RetrofitSingleton;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.reponse.GenericListResponse;
import mobi.moop.model.rotas.reponse.GenericResponse;
import mobi.moop.model.singleton.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class RotaReservasImpl {

    private Call<GenericResponse<ReservaBemComum>> callEfetuarReserva;
    private Call<GenericListResponse<BemComum>> callGetBensComuns;
    private Call<GenericListResponse<DisponibilidadeBem>> callGetDisponibilidadesBem;
    private Call<GenericListResponse<ReservaBemComum>> callGetReservas;
    private Call<GenericListResponse<DiaBemComum>> callLoadDiasDisponibilidades;
    private Call<ResponseBody> callCancelarReserva;

    public void efetuarReserva(final Context context, Long disponibilidadeId, final RotaReservas.ReservaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callEfetuarReserva = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).reservarDisponibilidade(usuario.getApiToken(), disponibilidadeId);
        callEfetuarReserva.enqueue(new Callback<GenericResponse<ReservaBemComum>>() {
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
                if (!call.isCanceled()) {
                    handler.onError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });

    }

    public void cancelEfetuarReservaRequisition() {
        try {
            callEfetuarReserva.cancel();
        } catch (Exception e) {

        }
    }

    public void getBensComuns(final Context context, Long condominioId, final RotaReservas.BemComunHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callGetBensComuns = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getBensComuns(usuario.getApiToken(), condominioId);
        callGetBensComuns.enqueue(new Callback<GenericListResponse<BemComum>>() {
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
                if (!call.isCanceled()) {
                    handler.onRecebimentoBensComunsErro(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetBensComunsRequisition() {
        try {
            callGetBensComuns.cancel();
        } catch (Exception e) {
        }
    }

    public void getDisponibilidades(final Context context, Long bemComumId, final RotaReservas.DisponibilidadesHandler handler, String dataDesejada) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callGetDisponibilidadesBem = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getDisponibilidadesBens(usuario.getApiToken(), bemComumId, dataDesejada);
        callGetDisponibilidadesBem.enqueue(new Callback<GenericListResponse<DisponibilidadeBem>>() {
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
                if (!call.isCanceled()) {
                    handler.onRecebimentoDisponibilidadesErro(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetDisponibilidadesRequisition() {
        try {
            callGetDisponibilidadesBem.cancel();
        } catch (Exception e) {

        }
    }

    public void getReservas(final Context context, Long condominioId, final RotaReservas.ReservaHandler handler) {
        Usuario usuario = UsuarioSingleton.I.getUsuarioLogado(context);
        callGetReservas = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getReservas(usuario.getApiToken(), condominioId);
        callGetReservas.enqueue(new Callback<GenericListResponse<ReservaBemComum>>() {
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
                if (!call.isCanceled()) {
                    handler.onError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetReservasRequisition() {
        try {
            callGetReservas.cancel();
        } catch (Exception e) {
        }
    }

    public void loadDiasDisponibilidadesBem(final Context context, Long bemId, Integer mes, Integer ano, final RotaReservas.DiasBensHandler handler) {
        Usuario usuarioLogado = UsuarioSingleton.I.getUsuarioLogado(context);
        Condominio condominioSelecionado = CondominioRepository.I.getCondominio(context);
        callLoadDiasDisponibilidades = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).getDiasBemComum(usuarioLogado.getApiToken(), condominioSelecionado.getId(), bemId, mes, ano);
        callLoadDiasDisponibilidades.enqueue(new Callback<GenericListResponse<DiaBemComum>>() {
            @Override
            public void onResponse(Call<GenericListResponse<DiaBemComum>> call, Response<GenericListResponse<DiaBemComum>> response) {
                if (response.isSuccessful()) {
                    handler.onDiasRecebidos(response.body().getData());
                } else {
                    handler.onError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<DiaBemComum>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelLoadDiasDisponibilidadesRequisition() {
        try {
            callLoadDiasDisponibilidades.cancel();
        } catch (Exception e) {
        }
    }

    public void cancelarReserva(final Context context, Long reservaId, final RotaReservas.CancelarReservaHandler handler) {
        Usuario usuarioLogado = UsuarioSingleton.I.getUsuarioLogado(context);
        callCancelarReserva = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaReservas.class).cancelarReserva(usuarioLogado.getApiToken(), reservaId);
        callCancelarReserva.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onReservaCancelada();
                } else {
                    handler.onCancelamentoError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onCancelamentoError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelCancelarReservaRequisition() {
        try {
            callCancelarReserva.cancel();
        } catch (Exception e) {
        }
    }
}
