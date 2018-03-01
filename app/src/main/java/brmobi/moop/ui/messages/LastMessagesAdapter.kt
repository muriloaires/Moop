package brmobi.moop.ui.messages

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 02/10/2017.
 */

class LastMessagesAdapter(val mPresenter: LastMessagesMvpPresenter<LastMessagesMvpView>) : RecyclerView.Adapter<LastMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ultima_mensagem, parent, false)
        return LastMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LastMessageViewHolder, position: Int) {
        val mensagem = mPresenter.getList()[position]
        holder.bindView(mensagem)
        holder.rootView.setOnClickListener {
            mPresenter.openMessagesActivity(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getList().size
    }
}
