package brmobi.moop.data.prefs

import android.content.Context
import android.content.SharedPreferences
import br.com.airescovit.clim.di.ApplicationContext
import br.com.airescovit.clim.di.PreferenceInfo
import brmobi.moop.data.DataManager
import brmobi.moop.utils.AppConstants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by murilo aires on 19/02/2018.
 */
@Singleton
class AppPreferenceHelper @Inject constructor(@ApplicationContext context: Context, @PreferenceInfo prefFileName: String) : PreferenceHelper {

    private var mPref: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    private val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
    private val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
    private val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
    private val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
    private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    private val PREFERENCE_LAST_SELECTED_CONDOMINIO = "lastSelectedCondominio"

    private val PREF_KEY_CURRENT_PROFILE_PIC = "PREF_KEY_PROFILE_PIC"

    override fun getCurrentProfilePic(): String? {
        return mPref.getString(PREF_KEY_CURRENT_PROFILE_PIC, null)
    }

    override fun setCurrentProfilePic(pictureUrl: String?) {
        mPref.edit().putString(PREF_KEY_CURRENT_PROFILE_PIC, pictureUrl).apply()
    }

    override fun getCurrentUserId(): Long {
        val userId: Long? = mPref.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX)
        return if (userId == null) AppConstants.NULL_INDEX else userId
    }

    override fun setCurrentUserId(id: Long?) {
        val userId: Long = id ?: AppConstants.NULL_INDEX
        mPref.edit().putLong(PREF_KEY_CURRENT_USER_ID, userId).apply()
    }

    override fun getCurrentUserName(): String {
        return mPref.getString(PREF_KEY_CURRENT_USER_NAME, null)
    }

    override fun setCurrentUserName(name: String?) {
        mPref.edit().putString(PREF_KEY_CURRENT_USER_NAME, name).apply()
    }

    override fun setCurrentUserEmail(email: String?) {
        mPref.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply()
    }

    override fun getCurrentUserEmail(): String {
        return mPref.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
    }

    override fun setAccessToken(accessToken: String?) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply()
    }

    override fun getCurrentAccessToken(): String {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    override fun setCurrentLoginMode(loginMode: DataManager.LoginMode) {
        mPref.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, loginMode.mType).apply()
    }

    override fun getCurrentLoginMode(): Int {
        return mPref.getInt(PREF_KEY_USER_LOGGED_IN_MODE, DataManager.LoginMode.LOGGED_IN_MODE_LOGGED_OUT.mType)
    }

    override fun getLastSelectedCondominium(): Long {
        return mPref.getLong(PREFERENCE_LAST_SELECTED_CONDOMINIO, AppConstants.NULL_INDEX)
    }

    override fun saveLastSelectedCondominium(condominiumId: Long) {
        mPref.edit().putLong(PREFERENCE_LAST_SELECTED_CONDOMINIO, condominiumId).apply()
    }

    override fun clearPreferences() {
        mPref.edit().clear().apply()
    }
}