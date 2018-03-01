package brmobi.moop.data.prefs

import brmobi.moop.data.DataManager

/**
 * Created by murilo aires on 19/02/2018.
 */
interface PreferenceHelper {

    fun getCurrentUserId(): Long
    fun setCurrentUserId(id: Long?)
    fun getCurrentUserName(): String
    fun setCurrentUserName(name: String?)
    fun setCurrentUserEmail(email: String?)
    fun getCurrentUserEmail(): String
    fun setAccessToken(accessToken: String?)
    fun getCurrentAccessToken(): String
    fun setCurrentLoginMode(loginMode: DataManager.LoginMode)
    fun getCurrentLoginMode(): Int
    fun getCurrentProfilePic(): String?
    fun setCurrentProfilePic(pictureUrl: String?)
    fun getLastSelectedCondominium(): Long
    fun saveLastSelectedCondominium(condominiumId: Long)
    fun clearPreferences()

}