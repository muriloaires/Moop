package brmobi.moop.data

import brmobi.moop.data.db.DbHelper
import brmobi.moop.data.media.MediaHelper
import brmobi.moop.data.network.ApiHelper
import brmobi.moop.data.prefs.PreferenceHelper

/**
 * Created by murilo aires on 19/02/2018.
 */
interface DataManager : DbHelper, PreferenceHelper, ApiHelper, MediaHelper {

    fun setUserAsLoggedOut()

    fun updateUserInfo(accessToken: String?, userId: Long?, loginMode: LoginMode, userName: String?, userEmail: String?, profilePicUrl : String?)

    enum class LoginMode(type: Int) {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGE_IN_MODE_SERVER(1);

        var mType: Int = type

    }
}