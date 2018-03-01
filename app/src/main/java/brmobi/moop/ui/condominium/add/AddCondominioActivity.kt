package brmobi.moop.ui.condominium.add

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import brmobi.moop.R
import brmobi.moop.data.network.model.Bloco
import brmobi.moop.data.network.model.CadastroCondominio
import brmobi.moop.ui.base.BaseActivity
import brmobi.moop.utils.AppConstants
import kotlinx.android.synthetic.main.activity_add_condominio.*
import java.io.Serializable
import javax.inject.Inject

class AddCondominioActivity : BaseActivity(), AddCondominiumMvpView {

    @Inject
    lateinit var mPresenter: AddCondominiumMvpPresenter<AddCondominiumMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_condominio)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        mPresenter.onViewReady()

    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun showCondominiunsFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.placeholder, CondominiunsFragment())
        ft.commit()
    }

    fun showBlocosFragment(condominiumIdExtraKey: String, condominiumIdExtraValue: Long, condominiumNameExtraValue: String) {
        mPresenter.onShowBlocsFragment(condominiumIdExtraValue, condominiumNameExtraValue)
        val ft = supportFragmentManager.beginTransaction()
        val fragment = BlocsFragment()
        val bundle = Bundle()
        bundle.putLong(condominiumIdExtraKey, condominiumIdExtraValue)
        fragment.arguments = bundle
        ft.replace(R.id.placeholder, fragment)
        ft.addToBackStack("main")
        ft.commit()
    }


    fun setTitutlo(titulo: String) {
        textEscolha!!.text = titulo
    }

    fun showDialogMoradorProprietario(mSelectedBloc: Bloco) {
        mPresenter.onShowDialogOwnerDweller(mSelectedBloc)

        val view = layoutInflater.inflate(R.layout.dialog_morador_proprietario, null)
        val builder = AlertDialog.Builder(this).setView(view)
                .setTitle(R.string.qual_seu_perfil)
        val dialog = builder.create()
        val rdMorador = view.findViewById<View>(R.id.rdMorador) as RadioButton
        val inputUnidade = view.findViewById<View>(R.id.inputUnidade) as EditText
        val btnConfirmar = view.findViewById<View>(R.id.btnConfirmar) as Button
        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnConfirmar.setOnClickListener {
            if (mPresenter.onDialogDwellerOwnerConfirmClick(inputUnidade.text.toString(), rdMorador.isChecked)) {
                dialog.dismiss()
            }
        }
        btnCancelar.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun showDialogConfirmation(selectedCondominiumName: String, selectedBlocName: String, unity: String, profileResId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.atencao))
                .setMessage("Deseja se cadastrar em " + selectedCondominiumName + ", Bloco " + selectedBlocName + ", unidade " + unity + " como " + getString(profileResId) + "?")
                .setPositiveButton(R.string.sim) { _, _ ->
                    mPresenter.onConfirmRegistration()
                }
                .setNegativeButton(R.string.cancelar) { dialog, _ ->
                    dialog.dismiss()
                }.show()
    }


    override fun finishWithOkResult() {
        setResult(RESULT_OK)
        finish()
    }

    fun showRegistroCondominioFragment(registerCondominium: Serializable) {
        val fragment = CondominiumRegisterFragment()
        val bundle = Bundle()
        bundle.putSerializable(AppConstants.REGISTER_CONDOMINIUM_EXTRA_KEY, registerCondominium)
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, fragment, TAG_REGISTRO_CONDOMINIO)
        ft.addToBackStack("main")
        ft.commit()
    }


    fun showAddBlocoFragment(registerCondominium: Serializable) {
        val fragment = AddBlocFragment()
        val bundle = Bundle()
        bundle.putSerializable(AppConstants.REGISTER_CONDOMINIUM_EXTRA_KEY, registerCondominium)
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.placeholder, fragment, TAG_REGISTRO_BLOCOS)
        ft.addToBackStack("main")
        ft.commit()
    }

    fun onCondominioCadastrado(body: CadastroCondominio) {
        onBackPressed()
        onBackPressed()
        showBlocosFragment(AppConstants.CONDOMINIO_ID_EXTRA_KEY, body.id, body.nome)
    }

    companion object {

        private val TAG_REGISTRO_CONDOMINIO = "registroCondominio"
        private val TAG_REGISTRO_BLOCOS = "registroBlocos"
    }
}
