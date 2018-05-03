package brmobi.moop.ui.reservation


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.data.network.model.DiaBemComum
import brmobi.moop.ui.base.BaseFragment
import com.imanoweb.calendarview.CalendarListener
import com.imanoweb.calendarview.DayDecorator
import com.imanoweb.calendarview.DayView
import kotlinx.android.synthetic.main.fragment_calendario_bem_comum.*
import java.io.Serializable
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SharedResourceCalendarFragment : BaseFragment(), SharedResourceCalendarMvpView {

    private var activity: DisponibilityActivity? = null
    private var lastDayViewSelected: View? = null

    @Inject
    lateinit var mPresenter: SharedResourceCalendarMvpPresenter<SharedResourceCalendarMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_calendario_bem_comum, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }
        return view
    }


    override fun setUp(view: View) {
        mPresenter.handleArguments(arguments)
        mPresenter.onViewReady()
        btn_solicitar_reserva.setOnClickListener {
            mPresenter.onBtnReserveClick()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.activity = context as DisponibilityActivity?
    }


    override fun configureCalendarView(calendar: Calendar) {
        calendar_view.refreshCalendar(calendar)
        calendar_view.setCalendarListener(object : CalendarListener {
            override fun onDateSelected(date: Date) {
                val calendarDayView = Calendar.getInstance()
                calendarDayView.time = date

                calendarDayView.set(Calendar.HOUR_OF_DAY, 0)
                calendarDayView.set(Calendar.MINUTE, 0)
                calendarDayView.set(Calendar.SECOND, 0)
                calendarDayView.set(Calendar.MILLISECOND, 100)

                val calendarX = Calendar.getInstance()
                calendarX.set(Calendar.HOUR_OF_DAY, 0)
                calendarX.set(Calendar.MINUTE, 0)
                calendarX.set(Calendar.SECOND, 0)
                calendarX.set(Calendar.MILLISECOND, 0)

                if (calendarDayView.time < calendarX.time) {
                    onError(getString(R.string.dia_nao_disponivel))
                } else {
                    for (diaBemComum: DiaBemComum in mDays) {
                        val calendarApi = Calendar.getInstance()
                        calendarApi.time = diaBemComum.data
                        if (calendarDayView.get(Calendar.DAY_OF_MONTH) == calendarApi.get(Calendar.DAY_OF_MONTH) &&
                                calendarDayView.get(Calendar.MONTH) == calendarApi.get(Calendar.MONTH) &&
                                calendarDayView.get(Calendar.YEAR) == calendarApi.get(Calendar.YEAR)) {
                            when {
                                diaBemComum.status == DiaBemComum.DIA_NAO_DISPONIVEL -> {
                                    onError(getString(R.string.dia_nao_disponivel))
                                }
                                diaBemComum.status == DiaBemComum.DIA_LOTADO -> {
                                    onError(getString(R.string.dia_nao_disponivel))
                                }
                                else -> {
                                    mPresenter.onDateSelected(date)
                                }
                            }


                        }

                    }
                }
            }

            override fun onMonthChanged(date: Date) {
                mPresenter.onMonthChanged(date)
            }
        })
        mPresenter.onCalendarConfigured()
    }

    override fun showAvailabilityFragment(dataExtraKey: String, dataExtraValue: String, sharedExtraKey: String, sharedValue: Serializable) {
        (activity as DisponibilityActivity).showDisponibilidadeFragment(dataExtraKey, dataExtraValue, sharedExtraKey, sharedValue)
    }

    private lateinit var mDays: List<DiaBemComum>

    override fun configureDayDecorators(days: List<DiaBemComum>, calendar: Calendar) {
        this.mDays = days
        calendar_view.visibility = View.VISIBLE
        val list = ArrayList<DayDecorator>()
        list.add(DisabledColorDecorator())
        calendar_view.decorators = list
        calendar_view.refreshCalendar(calendar)
    }

    companion object {

        fun getInstance(extraKey: String, extraSharedResource: Serializable): SharedResourceCalendarFragment {
            val fragment = SharedResourceCalendarFragment()
            val bundle = Bundle()
            bundle.putSerializable(extraKey, extraSharedResource)
            fragment.arguments = bundle
            return fragment
        }
    }

    private inner class DisabledColorDecorator : DayDecorator {

        override fun decorate(dayView: DayView) {
            val calendarDayView = Calendar.getInstance()
            calendarDayView.time = dayView.date

            calendarDayView.set(Calendar.HOUR_OF_DAY, 0)
            calendarDayView.set(Calendar.MINUTE, 0)
            calendarDayView.set(Calendar.SECOND, 0)
            calendarDayView.set(Calendar.MILLISECOND, 100)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (calendarDayView.time < calendar.time) {
                val color = Color.parseColor("#a9afb9")
                dayView.setTextColor(color)
            } else {
                for (diaBemComum: DiaBemComum in mDays) {
                    val calendarApi = Calendar.getInstance()
                    calendarApi.time = diaBemComum.data
                    if (calendarDayView.get(Calendar.DAY_OF_MONTH) == calendarApi.get(Calendar.DAY_OF_MONTH) &&
                            calendarDayView.get(Calendar.MONTH) == calendarApi.get(Calendar.MONTH) &&
                            calendarDayView.get(Calendar.YEAR) == calendarApi.get(Calendar.YEAR)) {
                        when {
                            diaBemComum.status == DiaBemComum.DIA_NAO_DISPONIVEL -> {
                                dayView.setTextColor(ContextCompat.getColor(activity, R.color.lightgrey))
                            }
                            diaBemComum.status == DiaBemComum.DIA_LOTADO -> {
                                dayView.setTextColor(Color.RED)
                            }
                            else -> {
                                dayView.setTextColor(Color.BLACK)
                            }
                        }


                    }

                }
            }
        }
    }
}// Required empty public constructor
