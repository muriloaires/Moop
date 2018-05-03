package brmobi.moop.ui.reservation


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brmobi.moop.R
import brmobi.moop.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_disponibilidades.*
import java.io.Serializable
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AvailabilityFragment : BaseFragment(), AvailabilityMvpView {

    private lateinit var adapter: AvailabilityAdapter

    @Inject
    lateinit var mPresenter: AvailabilityMvpPresenter<AvailabilityMvpView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_disponibilidades, container, false)
        val component = getActivityComponent()
        if (component != null) {
            component.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun setUp(view: View) {
        setupRecyclerView()
        mPresenter.handleArguments(arguments)
        mPresenter.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun finishWithOkResult() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }


    private fun setupRecyclerView() {
        recycler_disponibilidades.layoutManager = LinearLayoutManager(context)
        adapter = AvailabilityAdapter(mPresenter)
        recycler_disponibilidades.adapter = adapter
    }

    companion object {
        fun getInstance(dataExtraKey: String, dataExtraValue: String, sharedExtraKey: String, sharedValue: Serializable): AvailabilityFragment {
            val fragment = AvailabilityFragment()
            val bundle = Bundle()
            bundle.putString(dataExtraKey, dataExtraValue)
            bundle.putSerializable(sharedExtraKey, sharedValue)
            fragment.arguments = bundle
            return fragment
        }
    }
}// Required empty public constructor
