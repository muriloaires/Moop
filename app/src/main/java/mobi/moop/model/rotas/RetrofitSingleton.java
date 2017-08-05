package mobi.moop.model.rotas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by murilo aires on 21/03/2017.
 */

public enum RetrofitSingleton {
    INSTANCE;
    public static final String APP_TOKEN = "iFH46!^K7gD0LiTu%Wn6#uC9Ow!WeqRHObE8qw!5eJtOo3KWq^jT";
    private static RetrofitSingleton instance;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String BASE_URL = "http://app.moop.mobi/";
    public static final String API_V1 = "app_dev.php/api/v1/";


    public Retrofit getRetrofiInstance() {
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
    }

    public String getErrorBody(Response response) {
        try {
            return new JSONObject(response.errorBody().string()).getString("message");
        } catch (Exception e) {
            return "Algo errado ocorreu";
        }
    }
}
