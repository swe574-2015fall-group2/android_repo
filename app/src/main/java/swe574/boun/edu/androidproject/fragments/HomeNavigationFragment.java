package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import swe574.boun.edu.androidproject.HomeDrawerActivity;
import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.adapters.ListMeetingAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.tasks.FetchMyGroupsTask;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNavigationFragment extends HomeFragment {
    boolean first = true;
    private ListView mMyGroupsListView;
    private ListView mMyMeetingsListView;

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

        mMyMeetingsListView = (ListView) view.findViewById(R.id.listViewMyMeetings);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("authToken", App.mAuth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONRequest jsonRequest = new JSONRequest("http://162.243.18.170:9000/v1/meeting/myMeetings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Meeting> meetings = null;
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("status")) {
                        Boolean isSuccessful = Objects.equals(object.getString("status"), "success");
                        if (isSuccessful) {
                            if (object.get("result") != null) {
                                Object o = object.get("result");
                                if (o instanceof JSONArray) {
                                    meetings = new ArrayList<>();
                                    JSONArray array = (JSONArray) o;
                                    for (int i = 0; i < array.length(); i++) {
                                        Meeting meeting = Meeting.createFromJSON(array.getJSONObject(i));
                                        meetings.add(meeting);
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (meetings != null) {
                    if (meetings.size() > 0) {
                        ListMeetingAdapter adapter = new ListMeetingAdapter(mUser, getContext(), meetings);
                        mMyMeetingsListView.setAdapter(adapter);
                    } else {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No planned meetings are found."});
                        mMyMeetingsListView.setAdapter(adapter);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", "", error);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No planned meetings are found."});
                mMyMeetingsListView.setAdapter(adapter);

            }
        }, jsonObject);
        requestQueue.add(jsonRequest);

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
        ViewGroup view = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        if (!first) {
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
        }
        first = false;
    }
}
