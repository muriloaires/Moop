package mobi.moop.features.condominio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import mobi.moop.R;

/**
 * Created by murilo aires on 18/08/2017.
 */

public class UnidadesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocos, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        loadUnidades();
        return view;
    }

    private void setupRecyclerView() {

    }

    private void loadUnidades() {

    }
}
