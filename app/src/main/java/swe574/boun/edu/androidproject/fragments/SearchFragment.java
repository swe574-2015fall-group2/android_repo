package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.adapters.DividerItemDecoration;
import swe574.boun.edu.androidproject.adapters.SearchResultsAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.SearchResult;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.OnVolleyError;
import swe574.boun.edu.androidproject.requests.GenericErrorListener;
import swe574.boun.edu.androidproject.requests.SearchResponseListener;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class SearchFragment extends Fragment implements OnTaskCompleted, OnVolleyError {
    // Initialize Variables
    protected static final String USER_TOKEN = "user";
    protected static final String TAG_TOKEN = "tag";
    protected User mUser;
    private Tag mTag;
    // UI Variables
    private RecyclerView mSearchResultsRecyclerView;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(User mUser, Tag mTag) {
        Bundle args = new Bundle();
        args.putParcelable(USER_TOKEN, mUser);
        args.putParcelable(TAG_TOKEN, mTag);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(USER_TOKEN);
            mTag = getArguments().getParcelable(TAG_TOKEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        mSearchResultsRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.searchResultsRecyclerView);
        mSearchResultsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        mSearchResultsRecyclerView.setHasFixedSize(true);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("authToken", App.mAuth);
            jsonObject.accumulate("tagData", mTag.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONRequest searchJSONRequest = new JSONRequest("http://162.243.18.170:9000/v1/semantic/search", new SearchResponseListener(this),
                new GenericErrorListener(jsonObject, this), jsonObject);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(searchJSONRequest);
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

    @Override
    public void onTaskCompleted(Bundle extras) {
        SearchResult results = extras.getParcelable(SearchResponseListener.RESULT_TOKEN);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        SearchResultsAdapter adapter = new SearchResultsAdapter(results, mUser);
        mSearchResultsRecyclerView.setLayoutManager(manager);
        mSearchResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSearchResultsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onVolleyError(VolleyError error) {

    }

}
