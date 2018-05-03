package brmobi.moop.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import brmobi.moop.ui.feed.FeedFragment;
import brmobi.moop.ui.messages.MensagensFragment;
import brmobi.moop.ui.notifications.NotificationFragment;
import brmobi.moop.ui.reservation.ReservationFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ReservationFragment reservasFragment;
    private FeedFragment feedFragment;
    private MensagensFragment mensagensFragment;
    private NotificationFragment notificacoesFragment;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FeedFragment();
            case 1:
                return new ReservationFragment();
            case 2:
                return MensagensFragment.Companion.newInstance();

            default:
                return NotificationFragment.Companion.newInstance();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // save the appropriate reference depending on position
        switch (position) {
            case 0:
                feedFragment = (FeedFragment) createdFragment;
                break;
            case 1:
                reservasFragment = (ReservationFragment) createdFragment;
                break;
            case 2:
                mensagensFragment = (MensagensFragment) createdFragment;
                break;
            default:
                notificacoesFragment = (NotificationFragment) createdFragment;
        }
        return createdFragment;
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public ReservationFragment getReservasFragment() {
        return reservasFragment;
    }

    public FeedFragment getFeedFragment() {
        return feedFragment;
    }

    public MensagensFragment getMensagensFragment() {
        return mensagensFragment;
    }

    public NotificationFragment getNotificacoesFragment() {
        return notificacoesFragment;
    }
}
