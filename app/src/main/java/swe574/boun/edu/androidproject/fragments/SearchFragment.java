package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONRequest;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class SearchFragment extends Fragment {
    // Initialize Variables
    protected static final String USER_TOKEN = "user";
    protected User mUser;
    // UI Variables
    private RecyclerView mSearchResultsRecyclerView;
    // Network Variables
    private JSONRequest mSearchJSONRequest;

    public static SearchFragment newInstance(User mUser) {
        Bundle args = new Bundle();
        args.putParcelable(USER_TOKEN, mUser);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mUser = getArguments().getParcelable(USER_TOKEN);
        }
    }

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);

        mSearchResultsRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.searchResultsRecyclerView);


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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
