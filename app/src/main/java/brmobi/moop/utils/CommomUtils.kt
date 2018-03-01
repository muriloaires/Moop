package brmobi.moop.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class CommomUtils {
    companion object {
        fun showLoadingDialog(context: Context, title: String, message: String): ProgressDialog? {
            val progressDialog = ProgressDialog(context)
            progressDialog.show()
            progressDialog.setTitle(title)
            progressDialog.setMessage(message)
            progressDialog.isIndeterminate = true
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            return progressDialog
        }
    }
}