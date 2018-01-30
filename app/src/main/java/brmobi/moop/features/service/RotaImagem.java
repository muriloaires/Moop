package brmobi.moop.features.service;

import brmobi.moop.model.rotas.RetrofitSingleton;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by murilo aires on 11/12/2017.
 */

public interface RotaImagem {

    @POST(RetrofitSingleton.BASE_URL_IMAGE + "images")
    @Multipart
    Call<ResponseBody> upload(@Part MultipartBody.Part part);
}
