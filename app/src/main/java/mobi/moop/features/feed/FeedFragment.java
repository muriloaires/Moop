package mobi.moop.features.feed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.impl.RotaFeedImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements RotaFeed.FeedHandler {

    @BindView(R.id.recyclerFeed)
    RecyclerView recyclerView;

    private List<FeedItem> items;
    private FeedAdapter feedAdapter;
    private RotaFeedImpl rotaFeed = new RotaFeedImpl();

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadFeed();
        return view;
    }

    private void loadFeed() {
        rotaFeed.getFeed(getContext(), CondominioPreferences.I.getLastSelectedCondominio(getContext()), 10, items.size() - 1, this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = new ArrayList<>();
        items.add(null);
        feedAdapter = new FeedAdapter(items);
        recyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void onFeedReceived(List<FeedItem> items) {
        this.items.addAll(this.items.size() - 1, items);
        if (items.size() < 10) {
            this.items.remove(this.items.size() - 1);
        }
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFeedReceiveFail(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
