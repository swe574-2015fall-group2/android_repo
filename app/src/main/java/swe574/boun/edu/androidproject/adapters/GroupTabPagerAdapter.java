package swe574.boun.edu.androidproject.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import swe574.boun.edu.androidproject.fragments.DiscussionTabFragment;
import swe574.boun.edu.androidproject.fragments.GroupTabFragment;
import swe574.boun.edu.androidproject.fragments.MeetingTabFragment;
import swe574.boun.edu.androidproject.fragments.NoteTabFragment;

public class GroupTabPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;
    private int mNumOfTabs;
    private String mAuth;
    private String mGroup;

    public GroupTabPagerAdapter(FragmentManager fm, List<String> mTitles, int mNumOfTabs, String mAuth, String mGroup) {
        super(fm);
        this.mTitles = mTitles;
        this.mNumOfTabs = mNumOfTabs;
        this.mAuth = mAuth;
        this.mGroup = mGroup;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GroupTabFragment().newInstance(mAuth, mGroup);
                break;
            case 1:
                fragment = new MeetingTabFragment().newInstance(mAuth, mGroup);
                break;
            case 2:
                fragment = new DiscussionTabFragment().newInstance(mAuth, mGroup);
                break;
            case 3:
                fragment = new NoteTabFragment().newInstance(mAuth, mGroup);
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
