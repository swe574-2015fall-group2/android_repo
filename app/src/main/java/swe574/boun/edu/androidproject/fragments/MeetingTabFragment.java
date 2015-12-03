package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;

public class MeetingTabFragment extends Fragment {
    private final static String GROUP_TOKEN = "group";
    private final static String USER_TOKEN = "user";
    private Group mGroup;
    private String mAuth;

    public MeetingTabFragment() {
        // Required empty public constructor
    }

    public static MeetingTabFragment newInstance(Group GROUP, String USER_AUTH) {
        MeetingTabFragment fragment = new MeetingTabFragment();
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
        return inflater.inflate(R.layout.fragment_meeting, null, false);
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
                getActivity().setTitle("Meetings");
            }
        }
    }

}
