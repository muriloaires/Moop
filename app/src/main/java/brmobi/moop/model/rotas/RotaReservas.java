package brmobi.moop.model.rotas;

import java.util.List;

import brmobi.moop.model.entities.BemComum;
import brmobi.moop.model.entities.DiaBemComum;
import brmobi.moop.model.entities.DisponibilidadeBem;
import brmobi.moop.model.entities.ReservaBemComum;
import brmobi.moop.model.rotas.reponse.GenericListResponse;
import brmobi.moop.model.rotas.reponse.GenericResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by murilo aires on 12/08/2017.
 */

public interface RotaReservas {

    interface DiasBensHandler {
        void onDiasRecebidos(List<DiaBemComum> dias);

        void onError(String error);
    }


    interface BemComunHandler {
        void onBensComunsRecebidos(List<BemComum> bensComuns);

        void onRecebimentoBensComunsErro(String erro);

    }

    interface DisponibilidadesHandler {
        void onDisponilidadesRecebidas(GenericListResponse<DisponibilidadeBem> disponibilidades);

        void onRecebimentoDisponibilidadesErro(String error);
    }

    interface ReservaHandler {
        void onReservasRecebidas(List<ReservaBemComum> reservas);

        void onRecebimentoReservasError(String errorr);

        void onReservaEfetuada(ReservaBemComum reserva);

        void onError(String error);
    }

    interface CancelarReservaHandler {
        void onReservaCancelada();

        void onCancelamentoError(String error);
    }

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/condominio/{condominioId}/bens.json")
    Call<GenericListResponse<BemComum>> getBensComuns(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/bem/{bemId}/disponibilidades.json")
    Call<GenericListResponse<DisponibilidadeBem>> getDisponibilidadesBens(@Header("apiToken") String apiToken, @Path("bemId") Long bemId, @Query("dataDesejada") String dataDesejada);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/{disponibilidadeId}/reservar.json")
    Call<GenericResponse<ReservaBemComum>> reservarDisponibilidade(@Header("apiToken") String apiToken, @Path("disponibilidadeId") Long disponibilidadeId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/{condominioId}/reservas.json")
    Call<GenericListResponse<ReservaBemComum>> getReservas(@Header("apiToken") String apiToken, @Path("condominioId") Long bemId);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @GET(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/{condominioId}/{bemId}/dias.json")
    Call<GenericListResponse<DiaBemComum>> getDiasBemComum(@Header("apiToken") String apiToken, @Path("condominioId") Long condominioId, @Path("bemId") Long bemId, @Query("mes") Integer mes, @Query("ano") Integer ano);

    @Headers("appToken:" + RetrofitSingleton.APP_TOKEN)
    @POST(RetrofitSingleton.BASE_URL + RetrofitSingleton.API_V1 + "reserva/{reservaId}/cancelar.json")
    Call<ResponseBody> cancelarReserva(@Header("apiToken") String apiToken, @Path("reservaId") Long reservaId);
}
