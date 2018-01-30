package brmobi.moop.features.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class LoadViewHolder extends RecyclerView.ViewHolder {
    public LoadViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
