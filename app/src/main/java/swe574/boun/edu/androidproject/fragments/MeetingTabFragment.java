package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import swe574.boun.edu.androidproject.R;

public class MeetingTabFragment extends Fragment {
    private final static String GRUP_NAME_TOKEN = "name";
    private final static String USER_TOKEN = "user";
    private String USER_AUTH;
    private String GROUP_NAME;

    public MeetingTabFragment() {
        // Required empty public constructor
    }

    public static GroupTabFragment newInstance(String GROUP_NAME, String USER_AUTH) {
        GroupTabFragment fragment = new GroupTabFragment();
        Bundle args = new Bundle();
        args.putString(GRUP_NAME_TOKEN, GROUP_NAME);
        args.putString(USER_TOKEN, USER_AUTH);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            GROUP_NAME = (String) getArguments().get(GRUP_NAME_TOKEN);
            USER_AUTH = (String) getArguments().get(USER_TOKEN);
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

        if(this.isVisible()){
            if(isVisibleToUser){
                getActivity().setTitle("Meetings");
            }
        }
    }

}
