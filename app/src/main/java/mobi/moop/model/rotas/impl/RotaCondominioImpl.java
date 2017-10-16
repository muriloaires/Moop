package mobi.moop.model.rotas.impl;

import android.content.Context;

import mobi.moop.R;
import mobi.moop.model.entities.Bloco;
import mobi.moop.model.entities.CadastroCondominio;
import mobi.moop.model.entities.Condominio;
import mobi.moop.model.entities.Unidade;
import mobi.moop.model.entities.Usuario;
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

    private Call<GenericListResponse<Condominio>> callGetCondominiosUsuario;
    private Call<GenericListResponse<Condominio>> callGetCondominiosCEP;
    private Call<GenericListResponse<Bloco>> callgetBlocosCondominio;
    private Call<GenericListResponse<Unidade>> callGetUnidadeBloco;
    private Call<ResponseBody> callRegistrarUnidade;
    private Call<CadastroCondominio> callCadastroCondominio;

    public void getCondominiosUsuarioLogado(final Context context, final RotaCondominio.CondominiosHandler handler) {
        callGetCondominiosUsuario = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getCondominiosUsuario(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken());
        callGetCondominiosUsuario.enqueue(new Callback<GenericListResponse<Condominio>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Condominio>> call, Response<GenericListResponse<Condominio>> response) {
                if (response.isSuccessful()) {
                    CondominioRepository.I.saveCondominio(context, response.body().getData());
                    handler.onCondominiosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Condominio>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onGetCondominiosFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetCondominiosUsuarioRequisition() {
        try {
            callGetCondominiosUsuario.cancel();
        } catch (Exception e) {

        }
    }

    public void getCondominiosCep(final Context context, String cep, final RotaCondominio.CondominiosHandler handler) {
        callGetCondominiosCEP = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getCondominiosCEP(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), cep);
        callGetCondominiosCEP.enqueue(new Callback<GenericListResponse<Condominio>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Condominio>> call, Response<GenericListResponse<Condominio>> response) {
                if (response.isSuccessful()) {
                    handler.onCondominiosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Condominio>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onGetCondominiosFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetCondominiosCEPRequisition() {
        try {
            callGetCondominiosCEP.cancel();
        } catch (Exception e) {

        }
    }

    public void getBlocosCondominio(final Context context, Long condominioId, final RotaCondominio.BlocosHandler handler) {
        callgetBlocosCondominio = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getBlocosCondominio(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), condominioId);
        callgetBlocosCondominio.enqueue(new Callback<GenericListResponse<Bloco>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Bloco>> call, Response<GenericListResponse<Bloco>> response) {
                if (response.isSuccessful()) {
                    handler.onBlocosRecebidos(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Bloco>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onGetBlocosFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetBlocosCondominioRequisition() {
        try {
            callgetBlocosCondominio.cancel();
        } catch (Exception e) {

        }
    }

    public void getUnidadeBlocos(final Context context, Long condominioId, Long blocoId, final RotaCondominio.UnidadesHandler handler) {
        callGetUnidadeBloco = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).getUnidadesBloco(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), blocoId, condominioId);
        callGetUnidadeBloco.enqueue(new Callback<GenericListResponse<Unidade>>() {
            @Override
            public void onResponse(Call<GenericListResponse<Unidade>> call, Response<GenericListResponse<Unidade>> response) {
                if (response.isSuccessful()) {
                    handler.onUnidadesRecebidas(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GenericListResponse<Unidade>> call, Throwable t) {
                if (!call.isCanceled()) {
                    handler.onGetUnidadesError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelGetUnidadeBlocoRequisition() {
        try {
            callGetUnidadeBloco.cancel();
        } catch (Exception e) {

        }
    }

    public void registrarUnidade(final Context context, Long blocoId, boolean isProprietario, boolean isMorador, String unidade, final RotaCondominio.RegistroUnidadeHandler handler) {
        callRegistrarUnidade = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).registrarEmUnidade(UsuarioSingleton.I.getUsuarioLogado(context).getApiToken(), blocoId, isProprietario, isMorador, unidade);
        callRegistrarUnidade.enqueue(new Callback<ResponseBody>() {
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
                if (!call.isCanceled()) {
                    handler.onRegistrationFail(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelRegistrarUnidadeRequisition() {
        try {
            callRegistrarUnidade.cancel();
        } catch (Exception e) {

        }
    }

    public void cadastrarCondominio(final Context context, String cep, String nome, String logradouro, String numero, String telefone, boolean isHorizontal, final RotaCondominio.CadastroCondominioHandler handler) {
        Usuario usuarioLogado = UsuarioSingleton.I.getUsuarioLogado(context);
        callCadastroCondominio = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).cadastrarCondominio(usuarioLogado.getApiToken(), cep, nome, logradouro, numero, telefone, isHorizontal);
        callCadastroCondominio.enqueue(new Callback<CadastroCondominio>() {
            @Override
            public void onResponse(Call<CadastroCondominio> call, Response<CadastroCondominio> response) {
                if (response.isSuccessful()) {
                    handler.onCondominioCadastrado(response.body());
                } else {
                    handler.onCadastroError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<CadastroCondominio> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onCadastroError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }

    public void cancelarCadastroCondominioRequisition() {
        try {
            callCadastroCondominio.cancel();
        } catch (Exception e) {
        }
    }

    public void cadastrarBlocoCondominio(final Context context, Long condominioId, String nomeBloco, String numeroBloco, final RotaCondominio.AddBlocoHandler handler) {
        Usuario usuarioLogado = UsuarioSingleton.I.getUsuarioLogado(context);
        Call<ResponseBody> call = RetrofitSingleton.INSTANCE.getRetrofiInstance().create(RotaCondominio.class).cadastrarBloco(usuarioLogado.getApiToken(), condominioId, nomeBloco, numeroBloco);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    handler.onBlocoAdicionado(response.body());
                } else {
                    handler.onBlocoAddError(RetrofitSingleton.INSTANCE.getErrorBody(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    handler.onBlocoAddError(context.getString(R.string.algo_errado_ocorreu));
                }
            }
        });
    }
}
