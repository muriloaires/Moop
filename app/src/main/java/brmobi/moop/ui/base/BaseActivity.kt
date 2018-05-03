package brmobi.moop.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import brmobi.moop.MoopApplication
import brmobi.moop.R
import brmobi.moop.di.component.ActivityComponent
import brmobi.moop.di.component.DaggerActivityComponent
import brmobi.moop.di.module.ActivityModule
import brmobi.moop.ui.login.LoginActivity
import brmobi.moop.utils.CommomUtils
import brmobi.moop.utils.NetworkUtils

/**
 * Created by murilo aires on 19/02/2018.
 */
abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {

    var mProgressDialog: ProgressDialog? = null
    private lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as MoopApplication).mApplicationComponent)
                .build()
    }

    fun getActivityComponent(): ActivityComponent {
        return mActivityComponent
    }

    fun isLowerThanMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


    override fun showLoading(titleStringResId: Int, messageStringResId: Int) {
        hideLoading()
        mProgressDialog = CommomUtils.showLoadingDialog(this, getString(titleStringResId), getString(messageStringResId))
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    private fun showSnackBar(message: String) {

        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        snackbar.show()
    }

    override fun onError(error: String?) {
        if (error != null) {
            showSnackBar(error)
        } else {
            showSnackBar(getString(R.string.algo_errado_ocorreu))
        }
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.algo_errado_ocorreu), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(messageResId: Int) {
        showMessage(getString(messageResId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun openActivityOnTokenExpire() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}