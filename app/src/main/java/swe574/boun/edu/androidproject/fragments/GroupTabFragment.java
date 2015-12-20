package swe574.boun.edu.androidproject.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.OnTaskCompleted;
import swe574.boun.edu.androidproject.tasks.GetGroupCalendarTask;
import swe574.boun.edu.androidproject.tasks.LeaveGroupTask;

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

        final ListView list = (ListView) rootView.findViewById(R.id.group_calendar);
        GetGroupCalendarTask mCalendarTask = new GetGroupCalendarTask(mGroup, mUser, list, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Bundle extras) {
                if (list.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No planned meetings are found."});
                    list.setAdapter(adapter);
                }
            }
        });
        mCalendarTask.execute();

        Button leaveButton = (Button) rootView.findViewById(R.id.button_leave);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setTitle("Leave Group");
                builder.setMessage("Are you sure to leave this group?\n\nAs our terms of usage, our company holds all of the rights of the content" +
                        " that is posted, used, shared and uploaded content in our website in whatever form of content it has been used. The content owned by our company will be only used for display purposes only." +
                        " Pressing 'Yes' " +
                        "means that you agree on our terms of leaving your group. You can regain property of your content in case you rejoin the group in the future.");
                builder.setPositiveButton("Yes, I agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LeaveGroupTask task = new LeaveGroupTask(getActivity(), mGroup);
                        task.execute();
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });

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
