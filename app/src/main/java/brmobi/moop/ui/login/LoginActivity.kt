package brmobi.moop.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginActivityMpView {

    @Inject
    lateinit var mPresenter: LoginActivityMvpPresenter<LoginActivityMpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbarLogin)
        toolbarLogin.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbarLogin.setNavigationOnClickListener { onBackPressed() }
        showLoginFragment()

    }
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun showLoginFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, LoginFragment())
        ft.commit()
    }

    fun showRegistroFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, RegistroFragment())
        ft.addToBackStack("main")
        ft.commit()
    }

    fun hideToolbar() {
        toolbarLogin.visibility = View.GONE
    }

    fun showToolbar() {
        toolbarLogin.visibility = View.VISIBLE
    }


}
