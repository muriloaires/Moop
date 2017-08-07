package mobi.moop.features.feed;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import butterknife.OnClick;
import mobi.moop.R;
import mobi.moop.features.condominio.CondominioPreferences;
import mobi.moop.features.publicacoes.NewPostActivity;
import mobi.moop.model.entities.FeedItem;
import mobi.moop.model.rotas.RotaFeed;
import mobi.moop.model.rotas.impl.RotaFeedImpl;
import mobi.moop.utils.EndlessRecyclerOnScrollListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements RotaFeed.FeedHandler {

    private static final int WRITE_POST = 1;
    @BindView(R.id.recyclerFeed)
    RecyclerView recyclerView;

    @BindView(R.id.fab_createevent)
    FloatingActionButton fab;

    @BindView(R.id.refresh)
    SwipeRefreshLayout referesh;

    @OnClick(R.id.fab_createevent)
    public void fabAction(View view) {
        writeNewPost();
    }

    private List<FeedItem> items;
    private FeedAdapter feedAdapter;
    private RotaFeedImpl rotaFeed = new RotaFeedImpl();
    private EndlessRecyclerOnScrollListener scrollListener;

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
        referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeed(0);
            }
        });
        referesh.setColorSchemeResources(R.color.colorPrimary);
        loadFeed(items.size() - 1);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (referesh != null) {
            referesh.setRefreshing(false);
            referesh.destroyDrawingCache();
            referesh.clearAnimation();
        }
    }

    private void loadFeed(int offset) {
        recyclerView.removeOnScrollListener(scrollListener);
        rotaFeed.getFeed(getContext(), CondominioPreferences.I.getLastSelectedCondominio(getContext()), 10, offset, this);
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        items = new ArrayList<>();
        items.add(null);
        feedAdapter = new FeedAdapter(items);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0 && fab.isShown()) {
                    fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                        @Override
                        public void onHidden(FloatingActionButton fab) {
                            fab.setVisibility(View.GONE);
                        }
                    });
                } else if (dy < 0) {
                    fab.show();
                }
            }


        });
        scrollListener = new EndlessRecyclerOnScrollListener(manager, 3) {
            @Override
            public void onLoadMore(int current_page) {
                loadFeed(items.size() - 1);
            }
        };
    }

    @Override
    public void onFeedReceived(List<FeedItem> items, int offset) {
        if (referesh.isRefreshing()) {
            referesh.setRefreshing(false);
        }
        scrollListener.resetPreviousTotal();
        if (offset == 0) {
            this.items.clear();
            this.items.add(null);
        }
        this.items.addAll(this.items.size() - 1, items);
        if (items.size() < 10) {
            this.items.remove(this.items.size() - 1);
            feedAdapter.notifyDataSetChanged();
        } else {
            feedAdapter.notifyDataSetChanged();
            recyclerView.addOnScrollListener(scrollListener);

        }
    }

    @Override
    public void onFeedReceiveFail(String error) {
        if (referesh.isRefreshing()) {
            referesh.setRefreshing(false);
        }
        scrollListener.resetPreviousTotal();
        recyclerView.addOnScrollListener(scrollListener);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void writeNewPost() {
        startActivityForResult(new Intent(getContext(), NewPostActivity.class), WRITE_POST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case WRITE_POST:
                if (resultCode == Activity.RESULT_OK) {
                    FeedItem newItem = (FeedItem) data.getSerializableExtra("feedItem");
                    showNewItem(newItem);
                }
                break;
        }
    }

    private void showNewItem(FeedItem newItem) {
        recyclerView.scrollTo(0, 0);
        items.add(0, newItem);
        feedAdapter.notifyDataSetChanged();
        loadFeed(0);
    }
}
