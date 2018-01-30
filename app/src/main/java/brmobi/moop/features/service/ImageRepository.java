package brmobi.moop.features.service;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import brmobi.moop.MoopApplication;
import brmobi.moop.model.repository.dao.ImageDao;


/**
 * Created by murilo aires on 10/12/2017.
 */

public enum ImageRepository {
    I;

    public ImageDao getImageDao(Context context) {
        return ((MoopApplication) context.getApplicationContext()).getDaoSession().getImageDao();
    }

    public void insertOrReplace(Context context, List<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            try {
                Thread.sleep(10);
                getImageDao(context).insertOrReplace(images.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Image> getPersistedImages(Context context) {
        QueryBuilder<Image> qb = getImageDao(context).queryBuilder();
        return qb.list();
    }

    public List<Image> getImagesNaoPublicadas(Context context) {
        QueryBuilder<Image> qb = getImageDao(context).queryBuilder();
        qb.where(ImageDao.Properties.Sent.eq(false));
        qb.limit(400);
        return qb.list();
    }
}
