package brmobi.moop.model.repository;

import android.content.Context;

import java.util.List;

import brmobi.moop.MoopApplication;
import brmobi.moop.features.condominio.CondominioPreferences;
import brmobi.moop.model.entities.Condominio;
import brmobi.moop.model.repository.dao.CondominioDao;


/**
 * Created by murilo aires on 22/08/2017.
 */

public enum CondominioRepository {
    I;

    private CondominioDao getCondominioDao(Context context) {
        return ((MoopApplication) context.getApplicationContext()).getDaoSession().getCondominioDao();
    }

    public void saveCondominio(Context context, List<Condominio> condominios) {
        for (Condominio condominio : condominios) {
            getCondominioDao(context).insertOrReplace(condominio);
        }
    }

    public Condominio getCondominio(Context context) {
        return getCondominioDao(context).load(CondominioPreferences.I.getLastSelectedCondominio(context));
    }
}
