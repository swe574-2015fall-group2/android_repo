package swe574.boun.edu.androidproject.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import swe574.boun.edu.androidproject.fragments.DiscussionTabFragment;
import swe574.boun.edu.androidproject.fragments.GroupTabFragment;
import swe574.boun.edu.androidproject.fragments.MeetingTabFragment;
import swe574.boun.edu.androidproject.fragments.NoteTabFragment;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.User;

public class GroupTabPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;
    private int mNumOfTabs;
    private User mUser;
    private Group mGroup;

    public GroupTabPagerAdapter(FragmentManager fm, List<String> mTitles, int mNumOfTabs, User mUser, Group mGroup) {
        super(fm);
        this.mTitles = mTitles;
        this.mNumOfTabs = mNumOfTabs;
        this.mUser = mUser;
        this.mGroup = mGroup;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Class type = null;
        switch (position) {
            case 0:
                type = GroupTabFragment.class;
                break;
            case 1:
                type = MeetingTabFragment.class;
                break;
            case 2:
                type = DiscussionTabFragment.class;
                break;
            case 3:
                type = NoteTabFragment.class;
                break;
        }
        return ModelFragment.newInstance(mGroup, mUser, type);
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
