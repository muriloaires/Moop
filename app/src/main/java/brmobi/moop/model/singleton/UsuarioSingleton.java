package brmobi.moop.model.singleton;

import android.content.Context;

import brmobi.moop.model.entities.Usuario;
import brmobi.moop.model.repository.UsuarioRepository;


/**
 * Created by murilo aires on 24/07/2017.
 */

public enum UsuarioSingleton {
    I;

    public Usuario getUsuarioLogado(Context context) {
        return UsuarioRepository.I.getUsuarioLogado(context);
    }

    public boolean isUsuarioLogado(Context context) {
        return UsuarioRepository.I.getUsuarioLogado(context) != null;
    }
}
