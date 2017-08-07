package mobi.moop;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.appevents.AppEventsLogger;

import org.greenrobot.greendao.database.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mobi.moop.model.repository.dao.DaoMaster;
import mobi.moop.model.repository.dao.DaoSession;

/**
 * Created by murilo aires on 12/07/2017.
 */

public class MoopApplication extends Application {
    public static int screenWidth;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
        setUpDatabase();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        try {
            PackageInfo info = getPackageManager().getPackageInfo("mobi.moop", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void setUpDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "moop-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
