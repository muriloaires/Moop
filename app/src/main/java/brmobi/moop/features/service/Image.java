package brmobi.moop.features.service;

import android.os.Environment;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by murilo aires on 10/12/2017.
 */

@Entity
public class Image {
    public static final String[] PATHS = {Environment.getExternalStorageDirectory().getAbsolutePath() + "/Telegram/Telegram Images/",
//            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Telegram/Telegram Video/",
//            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Telegram/Telegram Documents/",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/Telegram/",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images/Sent/"
    };

    @Id(autoincrement = true)
    private Long id;
    
    private String path;

    private boolean sent;


    @Generated(hash = 1290610846)
    public Image(Long id, String path, boolean sent) {
        this.id = id;
        this.path = path;
        this.sent = sent;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }


    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getSent() {
        return this.sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
