package swe574.boun.edu.androidproject.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.UpdateGroupActivity;
import swe574.boun.edu.androidproject.adapters.ListViewAdapterListener;
import swe574.boun.edu.androidproject.adapters.ResourceListAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.ResourceQuery;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.network.JSONBuilder;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.RequestQueueBuilder;
import swe574.boun.edu.androidproject.tasks.GetGroupCalendarTask;
import swe574.boun.edu.androidproject.tasks.LeaveGroupTask;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

public class GroupTabFragment extends ModelFragment {
    private final int UPDATE_GROUP = 1;
    private RecyclerView mGroupResourceRecyclerView;
    private RequestQueue mRequestQueue;

    public GroupTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRequestQueue = Volley.newRequestQueue(getContext());
        final JSONObject requestJson = new JSONObject();
        try {
            requestJson.accumulate("authToken", App.mAuth);
            requestJson.accumulate("groupId", mGroup.getmID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONRequest getResourcesJSONRequest = new JSONRequest("http://162.243.18.170:9000/v1/resource/queryResourcesByGroup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = JSONBuilder.returnDefaultBuilder().create();
                Log.d("REQUEST", requestJson.toString());
                Log.d("RESPONSE", response);
                ResourceQuery resourceQuery = gson.fromJson(response, ResourceQuery.class);
                if (resourceQuery.getResult() != null) {
                    ResourceListAdapter resourceListAdapter = new ResourceListAdapter(resourceQuery.getResult(), new ListViewAdapterListener() {
                        @Override
                        public void onViewCreated(ViewGroup viewGroup) {
                            viewGroup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    });
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    mGroupResourceRecyclerView.setLayoutManager(manager);
                    mGroupResourceRecyclerView.setAdapter(resourceListAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, requestJson);
        mRequestQueue.add(getResourcesJSONRequest);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_group_home, null, false);
        mGroupResourceRecyclerView = (RecyclerView) rootView.findViewById(R.id.groupResourceRecyclerView);
        getActivity().setTitle(mGroup.getmName());

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

        TextView description = (TextView) rootView.findViewById(R.id.group_description);
        description.setText(mGroup.getmDescription());
        description.setMovementMethod(new ScrollingMovementMethod());

        TextView tags = (TextView) rootView.findViewById(R.id.group_tags);
        tags.setMovementMethod(new ScrollingMovementMethod());
        String tagText = "";
        for (Tag t : mGroup.getmTags()) {
            tagText += "#" + t.getTag() + ", ";
        }
        if (!tagText.equals("") && tagText.length() >= 2)
            tags.setText(tagText.substring(0, tagText.length() - 2));

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

        Button updateButton = (Button) rootView.findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateGroupActivity.class);
                intent.putExtra("user", mUser);
                intent.putExtra("group", mGroup);
                startActivityForResult(intent, UPDATE_GROUP);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_GROUP && resultCode == Activity.RESULT_OK) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("authToken", App.mAuth);
                jsonObject.accumulate("id", mGroup.getmID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONRequest jsonRequest = new JSONRequest("http://162.243.18.170:9000/v1/group/query", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Group g = Group.fromJsonString(new JSONObject(response));
                        getActivity().setTitle(g.getmName());
                        ViewGroup rootView = (ViewGroup) getView();
                        TextView description = (TextView) rootView.findViewById(R.id.group_description);
                        description.setText(mGroup.getmDescription());

                        TextView tags = (TextView) rootView.findViewById(R.id.group_tags);
                        String tagText = "";
                        for (Tag t : mGroup.getmTags()) {
                            tagText += "#" + t.getTag() + ", ";
                        }
                        if (!tagText.equals("") && tagText.length() >= 2)
                            tags.setText(tagText.substring(0, tagText.length() - 2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, jsonObject);
            mRequestQueue = RequestQueueBuilder.preapareSerialQueue(getContext());
            mRequestQueue.start();
            mRequestQueue.add(jsonRequest);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRequestQueue != null)
            mRequestQueue.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestQueue != null)
            mRequestQueue.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            mRequestQueue.stop();
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
                getActivity().setTitle("Home");
            }
        }
    }

}
