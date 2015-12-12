package swe574.boun.edu.androidproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import swe574.boun.edu.androidproject.HomeDrawerActivity;
import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewAllGroupsActivity;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.tasks.FetchAllGroupsTask;
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
        mMyGroupsTask = new FetchMyGroupsTask(view, mUser);
        mMyGroupsTask.execute();

        mRecommendTask = new FetchRecommendedGroupsTask(view, mUser);
        mRecommendTask.execute();
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
        if(requestCode == HomeDrawerActivity.NEW_GROUP && resultCode == Activity.RESULT_OK){
            mMyGroupsTask = new FetchMyGroupsTask((ViewGroup) getView(), mUser);
            mMyGroupsTask.execute();
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
