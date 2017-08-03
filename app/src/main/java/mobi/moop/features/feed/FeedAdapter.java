package mobi.moop.features.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.FeedItem;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter {
    private static final int LOAD_VIEW = 1;
    private static final int FEED_VIEW = 0;
    private final List<FeedItem> items;

    public FeedAdapter(List<FeedItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder vh;
        if (viewType == LOAD_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
            vh = new LoadViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            vh = new FeedViewHolder(view, this);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (items.get(position) != null) {
            ((FeedViewHolder) holder).bindView(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return LOAD_VIEW;
        } else {
            return FEED_VIEW;
        }
    }
}
