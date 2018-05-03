package brmobi.moop.ui.messages

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mensagem.*
import javax.inject.Inject

class MessagesActivity : BaseActivity(), MessagesMvpView {

    private lateinit var mensagensAdapter: MessagesAdapter

    @Inject
    lateinit var mPresenter: MessagesMvpPresenter<MessagesMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensagem)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)
        setSupportActionBar(toolbar_mensagens)
        toolbar_mensagens.setNavigationOnClickListener {
            onBackPressed()
        }
        btnEnviarMensagem.setOnClickListener {
            mPresenter.onBtnSendMessageClick(edt_mensagem.text.toString())
        }
        setupRecyclerView()
        mPresenter.handleIntent(intent)
        mPresenter.onViewReady()

    }

    override fun notifyDataSetChanged() {
        mensagensAdapter.notifyDataSetChanged()
    }

    override fun clearEditText() {
        edt_mensagem.setText("")
    }

    override fun setTitle(userDestinyName: String) {
        supportActionBar!!.title = "Mensagens com " + userDestinyName
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    private fun setupRecyclerView() {
        recycler_mensagem.layoutManager = LinearLayoutManager(this)
        mensagensAdapter = MessagesAdapter(mPresenter)
        recycler_mensagem.adapter = mensagensAdapter
    }

    override fun showMessageOptions(position: Int) {
        val builder = AlertDialog.Builder(this).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_apagar_mensagem)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    mPresenter.onDeleteMessageClick(position)
                }.setNegativeButton(R.string.cancelar) { dialog, which -> dialog.dismiss() }
        builder.show()
    }


}
