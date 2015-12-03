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
import swe574.boun.edu.androidproject.tasks.GetGroupCalendarTask;

public class GroupTabFragment extends Fragment {
    private final static String GROUP_TOKEN = "group";
    private final static String USER_TOKEN = "user";
    private Group mGroup;
    private String mAuth;

    public GroupTabFragment() {
        // Required empty public constructor
    }

    public static GroupTabFragment newInstance(final Group GROUP, final String USER_AUTH) {
        GroupTabFragment fragment = new GroupTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(GROUP_TOKEN, GROUP);
        args.putString(USER_TOKEN, USER_AUTH);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuth = (String) getArguments().getString(USER_TOKEN);
            mGroup = getArguments().getParcelable(GROUP_TOKEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_group_home, null, false);

        TextView groupName = (TextView) rootView.findViewById(R.id.groupName);
        groupName.setText(mGroup.getmName());

        ListView list = (ListView) rootView.findViewById(R.id.group_calendar);
        GetGroupCalendarTask mCalendarTask = new GetGroupCalendarTask(mGroup, mAuth, list, getContext());
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
