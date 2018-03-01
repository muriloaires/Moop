package brmobi.moop.ui.notifications

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import brmobi.moop.R

/**
 * Created by murilo aires on 21/12/2017.
 */

class NotificationAdapter(private val mPresenter: NotificationMvpPresenter<NotificationMvpView>) : RecyclerView.Adapter<NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificacao, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = mPresenter.getNotifications()[position]
        holder.bindView(notification)
        holder.rootView.setOnClickListener {
            mPresenter.onNotificationClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getNotifications().size
    }


}
