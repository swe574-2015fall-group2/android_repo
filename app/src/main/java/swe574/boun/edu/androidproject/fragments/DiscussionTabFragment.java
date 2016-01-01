package swe574.boun.edu.androidproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.NewCommunicateActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewCommunicateActivity;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.CommunicationType;
import swe574.boun.edu.androidproject.model.Discussion;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.RequestQueueBuilder;

public class DiscussionTabFragment extends ModelFragment {
    private final int ADD_DISCUSSION = 8;
    private ListView mDiscussionListView;
    private List<Discussion> mDiscussionList;
    private RequestQueue mRequestQueue;
    private JSONRequest mDiscussionRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(1, ADD_DISCUSSION, 0, "Create Discussion");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ADD_DISCUSSION) {
            Intent intent = new Intent(getContext(), NewCommunicateActivity.class);
            intent.putExtra("type", CommunicationType.DISCUSSION);
            intent.putExtra("group", mGroup);
            startActivityForResult(intent, 1);
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_discussion, null, false);

        mDiscussionListView = (ListView) viewGroup.findViewById(R.id.listDiscussions);
        mRequestQueue = RequestQueueBuilder.preapareSerialQueue(getContext());
        mRequestQueue.start();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("authToken", App.mAuth);
            jsonObject.accumulate("id", mGroup.getmID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mDiscussionRequest = new JSONRequest("http://162.243.18.170:9000/v1/discussion/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.has("status")) {
                        String status = responseObject.getString("status");
                        if (status.equals("success")) {
                            mDiscussionList = new ArrayList<>();
                            responseObject = responseObject.getJSONObject("result");
                            if (responseObject.has("discussionList")) {
                                JSONArray jsonArray = responseObject.getJSONArray("discussionList");
                                Gson gson = new Gson();
                                ArrayList<String> adapterHolder = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    mDiscussionList.add(gson.fromJson(object.toString(), Discussion.class));
                                    adapterHolder.add(mDiscussionList.get(i).getName());
                                }
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, adapterHolder);
                                mDiscussionListView.setAdapter(arrayAdapter);
                                mDiscussionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Discussion discussion = mDiscussionList.get(position);
                                        Intent intent = new Intent(getContext(), ViewCommunicateActivity.class);
                                        intent.putExtra("discussion", discussion);
                                        intent.putExtra("type", CommunicationType.DISCUSSION);
                                        startActivityForResult(intent, 2);
                                    }
                                });
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mDiscussionListView.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No discussions are found. Press here to create a discussion."});
                    mDiscussionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), NewCommunicateActivity.class);
                            intent.putExtra("type", CommunicationType.DISCUSSION);
                            intent.putExtra("group", mGroup);
                            startActivityForResult(intent, 1);
                        }
                    });
                    mDiscussionListView.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mDiscussionListView.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No discussions are found. Press here to create a discussion."});
                    mDiscussionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), NewCommunicateActivity.class);
                            intent.putExtra("type", CommunicationType.DISCUSSION);
                            intent.putExtra("group", mGroup);
                            startActivityForResult(intent, 1);
                        }
                    });
                    mDiscussionListView.setAdapter(adapter);

                }
            }
        }, jsonObject);
        mRequestQueue.add(mDiscussionRequest);
        return viewGroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1 || requestCode == 2) && resultCode == Activity.RESULT_OK) {
            mRequestQueue.add(mDiscussionRequest);
        }
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
                getActivity().setTitle("Discussions");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mRequestQueue != null)
            mRequestQueue.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRequestQueue != null)
            mRequestQueue.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            mRequestQueue.stop();
        }
    }

}
