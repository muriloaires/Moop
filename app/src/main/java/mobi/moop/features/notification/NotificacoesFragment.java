package mobi.moop.features.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.moop.R;
import mobi.moop.features.viewutils.Scrollable;

public class NotificacoesFragment extends Fragment implements Scrollable {


    public NotificacoesFragment() {
        // Required empty public constructor
    }

    public static NotificacoesFragment newInstance() {
        NotificacoesFragment fragment = new NotificacoesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notificacoes, container, false);
    }

    @Override
    public void scrollToTop() {

    }
}
