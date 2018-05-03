package brmobi.moop.utils;

import android.util.Patterns

class TextUtils {
    companion object {
        fun validEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }
    }
}