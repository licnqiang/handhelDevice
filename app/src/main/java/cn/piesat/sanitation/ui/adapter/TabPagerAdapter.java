package cn.piesat.sanitation.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private  String[] title;
    private List<Fragment> listFragment;

    public TabPagerAdapter(FragmentManager fm, String[] title, List<Fragment> listFragment) {
        super(fm);
        fm.beginTransaction().commitAllowingStateLoss();
        this.title = title;
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
