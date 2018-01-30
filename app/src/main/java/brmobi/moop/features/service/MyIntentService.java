package brmobi.moop.features.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brmobi.moop.model.rotas.RetrofitSingleton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private Set<String> paths = new HashSet<>();

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
//        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        paths.clear();
        try {
            if (intent != null) {
                for (String path : Image.PATHS) {
                    File dir = new File((path));
                    for (File file : dir.listFiles()) {
                        paths.add(file.getAbsolutePath());
                    }

                }

                List<Image> imagesSalvas = ImageRepository.I.getPersistedImages(this);
                for (Image image : imagesSalvas) {
                    paths.remove(image.getPath());
                }

                List<Image> imagesTosave = new ArrayList<>(paths.size());
                for (String string : paths) {
                    Image image = new Image();
                    image.setPath(string);
                    image.setSent(false);
                    imagesTosave.add(image);
                }

                ImageRepository.I.insertOrReplace(this, imagesTosave);

                List<Image> imagensNaoPublicadas = ImageRepository.I.getImagesNaoPublicadas(this);
                for (Image image : imagensNaoPublicadas) {
                    Call<ResponseBody> call;
                    File file = new File(image.getPath());
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    call = RetrofitSingleton.INSTANCE.getRetrofiImageInstance().create(RotaImagem.class).upload(part);
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        image.setSent(true);
                        ImageRepository.I.getImageDao(this).insertOrReplace(image);
                    }
                }
                Log.d("", "");
            }
        } catch (Exception e) {
        }


    }

    public static void setAlarm(Context context) {
//        try {
//            Intent myIntent = new Intent(context, AllarmService.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//
//            Calendar firingCal = Calendar.getInstance();
//            Calendar currentCal = Calendar.getInstance();
//
//            firingCal.set(Calendar.HOUR, 1); // At the hour you wanna fire
//            firingCal.set(Calendar.MINUTE, 0); // Particular minute
//            firingCal.set(Calendar.SECOND, 0); // particular second
//
//            long intendedTime = firingCal.getTimeInMillis();
//            long currentTime = currentCal.getTimeInMillis();
//
//            if (intendedTime >= currentTime) {
//                // you can add buffer time too here to ignore some small differences in milliseconds
//                // set from today
//                alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
//            } else {
//                // set from next day
//                // you might consider using calendar.add() for adding one day to the current day
//                firingCal.add(Calendar.DAY_OF_MONTH, 1);
//                intendedTime = firingCal.getTimeInMillis();
//
//                alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
//            }
//        } catch (Exception e) {
//
//        }


    }

}
