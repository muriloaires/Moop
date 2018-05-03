package brmobi.moop.ui.reservation


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import brmobi.moop.R
import brmobi.moop.utils.Scrollable
import brmobi.moop.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reservas.*

/**
 * A simple [Fragment] subclass.
 */
class ReservationFragment : BaseFragment(), Scrollable {
    private var adapter: PagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun setUp(view: View) {
        configureTabLayout()
    }



    fun configureTabLayout() {
        tabs.addTab(tabs.newTab().setCustomView(R.layout.tab_bens_comuns))
        tabs.addTab(tabs.newTab().setCustomView(R.layout.tab_minhas_reservas))
        adapter = PagerAdapter(childFragmentManager, tabs.tabCount)
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position
                val textTab = tab.customView!!.findViewById<View>(R.id.textTab) as TextView
                textTab.textSize = 14f
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textTab = tab.customView!!.findViewById<View>(R.id.textTab) as TextView
                textTab.textSize = 12f
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        tabs.getTabAt(0)!!.select()
    }

    inner class PagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

        override fun getPageTitle(position: Int): CharSequence {
            return if (position == 0) {
                getString(R.string.my_reservations)
            } else {
                getString(R.string.shared_resources)
            }
        }

        override fun getItem(position: Int): Fragment? {

            return when (position) {
                0 -> SharedResourcesFragment()
                1 -> MyReservationsFragment()
                else -> null
            }
        }


        override fun getCount(): Int {
            return mNumOfTabs
        }

    }

//    private fun openDisponilidadeActivity(bemComum: BemComum) {
//        val intent = Intent(context, DisponibilityActivity::class.java)
//        intent.putExtra("bemId", bemComum.id)
//        intent.putExtra("bemComumNome", bemComum.nome)
//        intent.putExtra("bemComumAvatar", bemComum.avatar)
//        intent.putExtra("bemComumTermos", bemComum.termosDeUso)
//        startActivityForResult(intent, MoopActivity.getREQUEST_RESERVA())
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == MoopActivity.getREQUEST_RESERVA() && resultCode == Activity.RESULT_OK) {
//            adapter!!.minhasReservasFragment!!.loadReservas()
//        }
//    }

    override fun scrollToTop() {}

}
