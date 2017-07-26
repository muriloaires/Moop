package mobi.moop;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

import org.greenrobot.greendao.database.Database;

import mobi.moop.model.repository.dao.DaoMaster;
import mobi.moop.model.repository.dao.DaoSession;

/**
 * Created by murilo aires on 12/07/2017.
 */

public class MoopApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
        setUpDatabase();
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
