package mobi.moop.features.reserva;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarioBemComumFragment extends Fragment {

    @BindView(R.id.calendar_view)
    CustomCalendarView calendarView;

    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private DisponibilidadesActivity activity;

    public CalendarioBemComumFragment() {
        // Required empty public constructor
    }

    public static CalendarioBemComumFragment getInstance(long bemId) {
        CalendarioBemComumFragment fragment = new CalendarioBemComumFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("bemId",bemId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario_bem_comum, container, false);
        ButterKnife.bind(this, view);
        configureCalendarView();
        loadDias();
        return view;
    }

    private void loadDias() {
        rotaReservas.loadDiasDisponibilidadesBem()
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (DisponibilidadesActivity) context;
    }


    private void configureCalendarView() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendarView.refreshCalendar(calendar);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                Log.d("", "");
            }

            @Override
            public void onMonthChanged(Date date) {
                Log.d("", "");
            }
        });
    }

}
