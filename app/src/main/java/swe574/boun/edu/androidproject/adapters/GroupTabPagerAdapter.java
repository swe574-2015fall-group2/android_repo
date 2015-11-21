package swe574.boun.edu.androidproject.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import swe574.boun.edu.androidproject.fragments.DiscussionFragment;
import swe574.boun.edu.androidproject.fragments.GroupHomeFragment;
import swe574.boun.edu.androidproject.fragments.MeetingFragment;
import swe574.boun.edu.androidproject.fragments.NoteFragment;

public class GroupTabPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;
    private int mNumOfTabs;

    public GroupTabPagerAdapter(FragmentManager fm, List<String> mTitles, int mNumOfTabs) {
        super(fm);
        this.mTitles = mTitles;
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GroupHomeFragment();
                break;
            case 1:
                fragment = new MeetingFragment();
                break;
            case 2:
                fragment = new DiscussionFragment();
                break;
            case 3:
                fragment = new NoteFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }


}
