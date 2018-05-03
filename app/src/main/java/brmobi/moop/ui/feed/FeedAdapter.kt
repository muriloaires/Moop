package brmobi.moop.ui.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 27/07/2017.
 */

class FeedAdapter(val mPresenter: FeedMvpPresenter<FeedMvpView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val vh: RecyclerView.ViewHolder
        if (viewType == LOAD_VIEW) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            vh = LoadViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
            vh = FeedViewHolder(view)
        }

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mPresenter.getItems()[position] != null) {
            (holder as FeedViewHolder).bindView(mPresenter.getItems()[position]!!)
            holder.textCountComments.setOnClickListener {
                mPresenter.btnCommentsClick(position)
            }
            holder.imgLike.setOnClickListener {
                mPresenter.onBtnLikeClick(position)
            }
            holder.imgUnlike.setOnClickListener {
                mPresenter.onBtnUnlikeClick(position)
            }
            holder.imgOptions.setOnClickListener {
                mPresenter.onOptionsClick(position)
            }
            holder.imgPost.setOnClickListener {
                mPresenter.onImgPostClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getItems().size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mPresenter.getItems()[position] == null) {
            LOAD_VIEW
        } else {
            FEED_VIEW
        }
    }


    companion object {
        private val LOAD_VIEW = 1
        private val FEED_VIEW = 0
    }
}
