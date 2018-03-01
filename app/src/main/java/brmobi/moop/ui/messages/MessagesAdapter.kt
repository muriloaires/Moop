package brmobi.moop.ui.messages

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 30/09/2017.
 */

class MessagesAdapter(private val mPresenter: MessagesMvpPresenter<MessagesMvpView>) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val comentario = mPresenter.getMessages()[position]
        holder.bindView(comentario)
        holder.rootView.setOnLongClickListener {
            mPresenter.onMessageLongClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getMessages().size
    }
}
