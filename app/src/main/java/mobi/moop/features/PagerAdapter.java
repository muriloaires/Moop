package mobi.moop.features;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import mobi.moop.features.feed.FeedFragment;
import mobi.moop.features.reserva.ReservasFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ReservasFragment reservasFragment;
    private FeedFragment feedFragment;

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
                return new ReservasFragment();

            default:
                return null;
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
                reservasFragment = (ReservasFragment) createdFragment;
                break;
        }
        return createdFragment;
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public ReservasFragment getReservasFragment() {
        return reservasFragment;
    }

    public FeedFragment getFeedFragment() {
        return feedFragment;
    }
}
