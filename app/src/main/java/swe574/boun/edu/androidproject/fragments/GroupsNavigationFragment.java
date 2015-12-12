package swe574.boun.edu.androidproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import swe574.boun.edu.androidproject.HomeDrawerActivity;
import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewAllGroupsActivity;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.tasks.FetchMyGroupsTask;
import swe574.boun.edu.androidproject.tasks.FetchRecommendedGroupsTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GroupsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsNavigationFragment extends HomeFragment {
    // Fragment parameters.
    private FetchMyGroupsTask mMyGroupsTask;
    private FetchRecommendedGroupsTask mRecommendTask;
    private int ADD_GROUP_ID;
    //UI parameters
    private Button mViewAllGroups;
    private ListView mMyGroupListView;
    private ListView mRecommendGroupsListView;

    public GroupsNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_groups, container, false);

        mViewAllGroups = (Button) view.findViewById(R.id.buttonViewAllGroups);
        mViewAllGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewAllGroupsActivity.class);
                i.putExtra("user", mUser);
                startActivity(i);
            }
        });
        mMyGroupListView = (ListView) view.findViewById(R.id.gridViewMyGroups);
        mMyGroupsTask = new FetchMyGroupsTask(view, mUser);
        mMyGroupsTask.execute();
        if (mMyGroupListView.getAdapter() == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No groups are found. Click here to create a group."});
            mMyGroupListView.setAdapter(adapter);
            mMyGroupListView.setOnTouchListener(new View.OnTouchListener() {
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

        mRecommendGroupsListView = (ListView) view.findViewById(R.id.gridViewRecommendedGroups);
        mRecommendTask = new FetchRecommendedGroupsTask(view, mUser);
        mRecommendTask.execute();
        if (mRecommendGroupsListView.getAdapter() == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No recommended groups are found. Click here to create a group."});
            mRecommendGroupsListView.setAdapter(adapter);
            mRecommendGroupsListView.setOnTouchListener(new View.OnTouchListener() {
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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ADD_GROUP_ID = Menu.FIRST + 1;
        menu.add(1, ADD_GROUP_ID, 1, "Create Group");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ADD_GROUP_ID) {
            Intent intent = new Intent(getActivity(), NewGroupActivity.class);
            startActivityForResult(intent, HomeDrawerActivity.NEW_GROUP);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HomeDrawerActivity.NEW_GROUP && resultCode == Activity.RESULT_OK) {
            mMyGroupsTask = new FetchMyGroupsTask((ViewGroup) getView(), mUser);
            mMyGroupsTask.execute();
            mRecommendTask = new FetchRecommendedGroupsTask((ViewGroup) getView(), mUser);
            mRecommendTask.execute();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle("Groups");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Groups");
    }

}
