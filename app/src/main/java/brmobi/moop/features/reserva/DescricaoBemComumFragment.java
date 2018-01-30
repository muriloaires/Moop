package brmobi.moop.features.reserva;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescricaoBemComumFragment extends Fragment {
    @BindView(R.id.bem_comum_nome)
    TextView bemComumNome;

    @BindView(R.id.img_bem_comum)
    ImageView imgBemComum;

    @BindView(R.id.termos_bem_comum)
    TextView termosBemComum;
    private DisponibilidadesActivity activity;

    public static DescricaoBemComumFragment getInstance(String bemComumNome, String bemComumAvatar, String bemComumTermos, long bemId) {
        DescricaoBemComumFragment fragment = new DescricaoBemComumFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bemComumAvatar", bemComumAvatar);
        bundle.putString("bemComumNome", bemComumNome);
        bundle.putString("bemComumTermos", bemComumTermos);
        bundle.putLong("bemId", bemId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.btn_eu_concordo)
    public void btnEuConcordoAction(View view) {
        activity.showCalendarioFragment();
    }

    public DescricaoBemComumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descricao_bem_comum, container, false);
        ButterKnife.bind(this, view);
        bemComumNome.setText(getArguments().getString("bemComumNome"));
        if (getArguments().getString("bemComumAvatar") != null && !getArguments().getString("bemComumAvatar").equals("")) {
            Picasso.with(getContext()).load(getArguments().getString("bemComumAvatar")).into(imgBemComum);
        }
        termosBemComum.setText(getArguments().getString("bemComumTermos"));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (DisponibilidadesActivity) context;
    }
}
