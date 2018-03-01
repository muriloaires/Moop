package brmobi.moop.ui.dwellers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R
import com.squareup.picasso.Picasso

/**
 * Created by murilo aires on 16/10/2017.
 */

class ApproveDwellersAdapter(private val mPresenter: ApproveDwellersMvpPresenter<ApproveDwellersMvpView>) : RecyclerView.Adapter<ApproveDwellerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproveDwellerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aprovar_morador, parent, false)
        return ApproveDwellerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApproveDwellerViewHolder, position: Int) {
        val perfil = mPresenter.getDwellers()[position]
        if (perfil.perfil.avatar != null && perfil.perfil.avatar != "") {
            Picasso.with(holder.imgAvatar.context).load(perfil.perfil.avatar).placeholder(R.drawable.placeholder_avatar).error(R.drawable.placeholder_avatar).into(holder.imgAvatar)
        } else {
            Picasso.with(holder.imgAvatar.context).load(R.drawable.placeholder_avatar).into(holder.imgAvatar)
        }
        holder.textNome.text = perfil.perfil.nome
        holder.textUnidade.text = "Bloco " + perfil.unidadeHabitacional.bloco.nome + " Unidade " + perfil.unidadeHabitacional.numero!!.toString()
        holder.btnApprove.setOnClickListener { mPresenter.onBtnApproveClick(position) }
        holder.btnDesapprove.setOnClickListener { mPresenter.onBtnDesaproveClick(position) }
    }

    override fun getItemCount(): Int {
        return mPresenter.getDwellers().size
    }
}
