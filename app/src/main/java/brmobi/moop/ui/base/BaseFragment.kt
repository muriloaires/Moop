package brmobi.moop.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import brmobi.moop.di.component.ActivityComponent
import brmobi.moop.utils.CommomUtils

/**
 * Created by murilo aires on 19/02/2018.
 */
abstract class BaseFragment : Fragment(), MvpView {

    private var mActivity: BaseActivity? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
            this.mActivity!!.onFragmentAttached()
        }
    }

    override fun showLoading(titleStringResId: Int, messageStringResId: Int) {
        hideLoading()
        mProgressDialog = CommomUtils.showLoadingDialog(mActivity as Context, getString(titleStringResId), getString(messageStringResId))
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onError(error: String?) {
        if (mActivity != null) {
            mActivity!!.onError(error)
        }
    }

    override fun onError(@StringRes resId: Int) {
        if (mActivity != null) {
            mActivity!!.onError(resId)
        }
    }

    override fun showMessage(message: String?) {
        if (mActivity != null) {
            mActivity!!.showMessage(message)
        }
    }

    override fun showMessage(@StringRes messageResId: Int) {
        if (mActivity != null) {
            mActivity!!.showMessage(messageResId)
        }
    }

    override fun isNetworkConnected(): Boolean {
        return if (mActivity != null) {
            mActivity!!.isNetworkConnected()
        } else false
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    override fun openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity!!.openActivityOnTokenExpire()
        }
    }

    fun getActivityComponent(): ActivityComponent? {
        return if (mActivity != null) {
            mActivity!!.getActivityComponent()
        } else null
    }

    fun getBaseActivity(): BaseActivity? {
        return mActivity
    }


    protected abstract fun setUp(view: View)

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}