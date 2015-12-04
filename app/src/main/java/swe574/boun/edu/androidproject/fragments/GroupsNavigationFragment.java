package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewAllGroupsActivity;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.tasks.FetchMyGroupsTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GroupsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsNavigationFragment extends HomeFragment {
    // Fragment parameters.
    FetchMyGroupsTask mTask;
    private int ADD_GROUP_ID;
    //UI parameters
    private Button mViewAllGroups;
    private ListView mMyGroupView;
    private ListView mRecGroupView;

    public GroupsNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().findViewById(R.id.search_bar_layout).setVisibility(View.GONE);
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

        mMyGroupView = (ListView) view.findViewById(R.id.gridViewMyGroups);
        mRecGroupView = (ListView) view.findViewById(R.id.gridViewRecommendedGroups);
        mTask = new FetchMyGroupsTask(view, mUser);
        mTask.execute();
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
            intent.putExtra("user", mUser);
            startActivity(intent);
        }
        return true;
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
