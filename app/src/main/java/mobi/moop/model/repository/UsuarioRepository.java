package mobi.moop.model.repository;

import android.content.Context;

import mobi.moop.MoopApplication;
import mobi.moop.model.entities.Usuario;
import mobi.moop.model.repository.dao.UserDao;
import mobi.moop.model.repository.dao.UsuarioDao;

/**
 * Created by murilo aires on 24/07/2017.
 */

public enum UsuarioRepository {
    I;

    public void saveUsuarioLogado(Context context, Usuario usuario) {
        usuario.restructureData();
        getUserDao(context).insertOrReplace(usuario.getUser());
        getUsuarioDao(context).insertOrReplace(usuario);
    }

    private UserDao getUserDao(Context context) {
        return ((MoopApplication) context.getApplicationContext()).getDaoSession().getUserDao();
    }

    public Usuario getUsuarioLogado(Context context) {
        try {
            return getUsuarioDao(context).loadAll().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private UsuarioDao getUsuarioDao(Context context) {
        return ((MoopApplication) context.getApplicationContext()).getDaoSession().getUsuarioDao();
    }

}
