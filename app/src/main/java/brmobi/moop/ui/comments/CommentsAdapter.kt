package brmobi.moop.ui.comments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 11/08/2017.
 */

class CommentsAdapter(val mPresenter: CommentsMvpPresenter<CommentsMvpView>) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comentario = mPresenter.getComments()[position]
        holder.bindView(comentario)
        holder.rootView.setOnLongClickListener {
            mPresenter.onItemLongClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getComments().size
    }
}
