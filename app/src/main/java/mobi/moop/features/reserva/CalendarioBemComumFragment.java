package mobi.moop.features.reserva;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.DiaBemComum;
import mobi.moop.model.rotas.RotaReservas;
import mobi.moop.model.rotas.impl.RotaReservasImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarioBemComumFragment extends Fragment implements RotaReservas.DiasBensHandler {

    @BindView(R.id.calendar_view)
    CustomCalendarView calendarView;

    private RotaReservasImpl rotaReservas = new RotaReservasImpl();
    private DisponibilidadesActivity activity;
    private View lastDaySelected;

    public CalendarioBemComumFragment() {
        // Required empty public constructor
    }

    public static CalendarioBemComumFragment getInstance(long bemId) {
        CalendarioBemComumFragment fragment = new CalendarioBemComumFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("bemId", bemId);
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
        Calendar calendar = Calendar.getInstance();
        rotaReservas.loadDiasDisponibilidadesBem(getContext(), getArguments().getLong("bemId"), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (DisponibilidadesActivity) context;
    }


    private void configureCalendarView() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendarView.refreshCalendar(calendar);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                List<DayDecorator> decorators = calendarView.getDecorators();
                calendarView.setDecorators(decorators);
                calendarView.refreshCalendar(Calendar.getInstance());
            }

            @Override
            public void onMonthChanged(Date date) {
                Log.d("", "");
            }
        });
    }

    @Override
    public void onDiasRecebidos(List<DiaBemComum> dias) {
        List<DayDecorator> list = new ArrayList<>();
        for (final DiaBemComum dia : dias) {
            DayDecorator dayDecorator = new DayDecorator() {
                @Override
                public void decorate(final DayView dayView) {
                    dayView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dia.getStatus().equals(DiaBemComum.DIA_NAO_DISPONIVEL)) {
                                Toast.makeText(activity, getString(R.string.dia_nao_disponivel), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            if (CalendarioBemComumFragment.this.lastDaySelected != null) {
                                lastDaySelected.setBackgroundColor(Color.WHITE);
                            }
                            lastDaySelected = v;
                        }
                    });
                    if (dia.getStatus().equals(DiaBemComum.DIA_NAO_DISPONIVEL)) {
                        dayView.setTextColor(getContext().getResources().getColor(R.color.lightgrey));
                    } else if (dia.getStatus().equals(DiaBemComum.DIA_LOTADO)) {
                        dayView.setTextColor(Color.RED);
                    } else {
                        dayView.setTextColor(Color.BLACK);
                    }
                }
            };
            list.add(dayDecorator);

        }
        calendarView.setDecorators(list);
        calendarView.refreshCalendar(Calendar.getInstance());
    }

    @Override
    public void onError(String error) {

    }
}
