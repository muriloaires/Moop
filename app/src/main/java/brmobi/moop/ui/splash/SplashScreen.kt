package brmobi.moop.ui.splash

import android.content.Intent
import android.os.Bundle
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import brmobi.moop.ui.login.LoginActivity
import brmobi.moop.ui.main.MoopActivity
import javax.inject.Inject

class SplashScreen : BaseActivity(), SplasMvpView {

    @Inject
    lateinit var mPresenter: SplashMvpPresenter<SplasMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        mPresenter.onViewReady()

    }

    override fun openMoopActivity() {
        startActivity(Intent(this, MoopActivity::class.java))
        finish()
    }

    override fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}