package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.OnTaskCompleted;
import swe574.boun.edu.androidproject.tasks.GetGroupMeetingsTask;

public class MeetingTabFragment extends ModelFragment {
    public MeetingTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_meeting, null, false);

        final ListView meetingList = (ListView) viewGroup.findViewById(R.id.listMeetings);
        GetGroupMeetingsTask mTask = new GetGroupMeetingsTask(mGroup, mUser, meetingList, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Bundle extras) {
                if (meetingList.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No planned meetings are found."});
                    meetingList.setAdapter(adapter);
                }
            }
        });
        mTask.execute();
        return viewGroup;
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
