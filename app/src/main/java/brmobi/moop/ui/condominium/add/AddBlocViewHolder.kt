package brmobi.moop.ui.condominium.add

import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.item_add_bloco.view.*

/**
 * Created by murilo aires on 12/12/2017.
 */

class AddBlocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btnDeleteBloc = itemView.btnExcluirBloco
    val btnMore = itemView.btnMais
    val editBlocName = itemView.editNomeBloco

    init {
        ButterKnife.bind(this, itemView)
    }

    fun showBtnAdd() {
        btnMore.visibility = View.VISIBLE
        btnDeleteBloc.visibility = View.GONE
    }

    fun hideBtnAdd() {
        btnMore.visibility = View.GONE
        btnDeleteBloc!!.visibility = View.VISIBLE
    }

    fun bindView(bloco: String) {
        editBlocName.setText(bloco)
    }

    fun requestFocus() {
        editBlocName.requestFocus()
    }
}
