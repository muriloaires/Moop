package brmobi.moop.ui.comments

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import brmobi.moop.R
import brmobi.moop.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detalhe_post.*
import javax.inject.Inject

class CommentsActivity : BaseActivity(), CommentsMvpView {

    private lateinit var comentariosAdapter: CommentsAdapter


    @Inject
    lateinit var mPresenter: CommentsMvpPresenter<CommentsMvpView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_post)
        getActivityComponent().inject(this)
        mPresenter.onAttach(this)

        setSupportActionBar(toolbarComentarios)
        toolbarComentarios.setNavigationIcon(R.drawable.ic_back)
        toolbarComentarios.setNavigationOnClickListener { onBackPressed() }
        setupRecyclerView()
        btnEnviarComentario.setOnClickListener {
            mPresenter.onBtnSendCommentClick(edtComentario.text.toString())
        }
        mPresenter.handleIntent(intent)
        mPresenter.onViewReady()

    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    private fun setupRecyclerView() {
        recyclerComentarios!!.layoutManager = LinearLayoutManager(this)
        comentariosAdapter = CommentsAdapter(mPresenter)
        recyclerComentarios!!.adapter = comentariosAdapter
    }

    override fun notifyDataSetChanged() {
        comentariosAdapter.notifyDataSetChanged()
    }

    override fun showDeleteOptions(position: Int) {
        val builder = AlertDialog.Builder(this).setTitle(R.string.atencao)
                .setMessage(R.string.deseja_apagar_comentario)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    mPresenter.onDeleteCommentClick(position)
                }.setNegativeButton(R.string.cancelar) { dialog, which ->
                    dialog.dismiss()
                }
        builder.show()
    }

    override fun clearEditTextComment() {
        edtComentario.setText("")
    }
}
