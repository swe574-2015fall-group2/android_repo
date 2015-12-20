package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import swe574.boun.edu.androidproject.HomeDrawerActivity;
import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.model.OnTaskCompleted;
import swe574.boun.edu.androidproject.tasks.FetchMyGroupsTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNavigationFragment extends HomeFragment {
    private ListView mMyGroupsListView;

    public HomeNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        mMyGroupsListView = (ListView) view.findViewById(R.id.gridViewMyGroups);
        FetchMyGroupsTask mTask = new FetchMyGroupsTask(view, mUser, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Bundle extras) {
                if (mMyGroupsListView.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No groups are found. Click here to create a group."});
                    mMyGroupsListView.setAdapter(adapter);
                    mMyGroupsListView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                Intent i = new Intent(getContext(), NewGroupActivity.class);
                                startActivityForResult(i, HomeDrawerActivity.NEW_GROUP);
                            }
                            return true;
                        }
                    });
                }
            }
        });
        mTask.execute();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle("Home");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
        FetchMyGroupsTask mTask = new FetchMyGroupsTask(mMyGroupsListView, mUser, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Bundle extras) {
                if (mMyGroupsListView.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No groups are found. Click here to create a group."});
                    mMyGroupsListView.setAdapter(adapter);
                    mMyGroupsListView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                Intent i = new Intent(getContext(), NewGroupActivity.class);
                                startActivityForResult(i, HomeDrawerActivity.NEW_GROUP);
                            }
                            return true;
                        }
                    });
                }
            }
        });
    }
}
