package brmobi.moop.ui.condominium

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_meu_condominio.*
import javax.inject.Inject

class MyCondominiumActivity : BaseActivity(), MyCondominiumMvpView {

    @Inject
    lateinit var mPresenter: MyCondominiumMvpPresenter<MyCondominiumMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meu_condominio)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener {
            onBackPressed()
        }
        btnGerarSenha.setOnClickListener {
            mPresenter.onBtnGeneratePasswordClick()
        }
        btnGerenciarCondominio.setOnClickListener {
            mPresenter.onManageCondominiumClick()
        }
        senha_gerada.setOnClickListener {
            mPresenter.onNewPasswordClick()
        }
        toque_para_copiar.setOnClickListener {
            mPresenter.onNewPasswordClick()
        }
        mPresenter.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    override fun openMoopSite(stringResId: Int) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(stringResId)))
        startActivity(browserIntent)
    }

    override fun copyNewPassword() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Senha", senha_gerada.text.toString())
        clipboard.primaryClip = clip
        Toast.makeText(this, "Senha copiada para área de transferência", Toast.LENGTH_SHORT).show()
    }

    override fun setSyindicLayoutVisibility(visibility: Int) {
        sindico_layout.visibility = visibility
    }

    override fun setUserEmail(currentUserEmail: String) {
        text_email.text = currentUserEmail
    }

    override fun setCondominiumName(name: String) {
        text_nome.text = name
    }

    override fun setCondominiumZipCode(zipCode: String) {
        textCep.text = "CEP: " + zipCode
    }

    override fun setCondominiumStreet(street: String) {
        textLogradouro.text = "Logradouro: " + street
    }

    override fun seCondominiumOrientation(resId: Int) {
        text_orientacao.text = getString(resId)
    }

    override fun setCondominiumOrientationDrawable(drawableResId: Int) {
        imgOrientacao.setImageResource(drawableResId)
    }

    override fun setCondominiumSyndicName(syndicName: String) {
        textNomeSindico.text = syndicName
    }

    override fun setCondominiumPhoneNumber(phone: String) {
        textTelefone.text = phone
    }

    override fun setTotalDwellersText(totalDwellersCount: Int) {
        textTotalMoradores.text = totalDwellersCount.toString()
    }

    override fun showTexGeneratedPassword() {
        senha_gerada.visibility = View.VISIBLE
    }

    override fun showClickToCopyText() {
        toque_para_copiar.visibility = View.VISIBLE
    }

    override fun setNewPasswordTex(senha: String) {
        senha_gerada.text = senha
    }
}
