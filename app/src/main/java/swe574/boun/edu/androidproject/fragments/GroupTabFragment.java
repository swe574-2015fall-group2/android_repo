package swe574.boun.edu.androidproject.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.tasks.GetGroupCalendarTask;

public class GroupTabFragment extends ModelFragment {

    public GroupTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_group_home, null, false);

        TextView groupName = (TextView) rootView.findViewById(R.id.groupName);
        groupName.setText(mGroup.getmName());

        ListView list = (ListView) rootView.findViewById(R.id.group_calendar);
        GetGroupCalendarTask mCalendarTask = new GetGroupCalendarTask(mGroup, mUser, list);
        mCalendarTask.execute();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                getActivity().setTitle("Home");
            }
        }
    }

}
