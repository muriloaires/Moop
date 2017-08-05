package mobi.moop.features.condominio;

import android.content.Context;
import android.content.SharedPreferences;

import mobi.moop.R;

/**
 * Created by murilo aires on 26/07/2017.
 */

public enum CondominioPreferences {
    I;

    private static final String PREFERENCE_LAST_SELECTED_CONDOMINIO = "lastSelectedCondominio";

    public Long getLastSelectedCondominio(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_configs), Context.MODE_PRIVATE);
        return preferences.getLong(PREFERENCE_LAST_SELECTED_CONDOMINIO, -1);
    }

    public void saveLastSelectedCondominio(Context context, Long condominioId) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_configs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREFERENCE_LAST_SELECTED_CONDOMINIO, condominioId);
        editor.apply();
    }

    public void deletePreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_configs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().clear();
    }
}
