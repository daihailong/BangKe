package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Hailong on 2016/7/5.
 */
public class BaseVPAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    String[] titles;

    public BaseVPAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseVPAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        this(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

