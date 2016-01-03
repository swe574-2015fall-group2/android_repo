package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import swe574.boun.edu.androidproject.adapters.ListViewAdapterListener;
import swe574.boun.edu.androidproject.adapters.ResourceListAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.ContactDetails;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.ResourceQuery;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.network.JSONBuilder;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.ui.ResourceViewHolder;

public class ViewMeetingActivity extends AppCompatActivity {
    private Meeting mMeeting;
    private TextView mMeetingTags;
    private TextView mMeetingLocation;
    private TextView mMeetingDay;
    private TextView mMeetingMonth;
    private TextView mMeetingYear;
    private ListView mMeetingAgenda;
    private ListView mMeetingToDo;
    private TextView mMeetingDetails;
    private RecyclerView mMeetingresources;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        Intent i = getIntent();
        final Meeting meeting = i.getParcelableExtra("meeting");
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.accumulate("authToken", App.mAuth);
            requestObject.accumulate("id", meeting.getmID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMeetingTags = (TextView) findViewById(R.id.meetingTags);
        mMeetingLocation = (TextView) findViewById(R.id.MeetingLocation);
        mMeetingDay = (TextView) findViewById(R.id.MeetingDay);
        mMeetingMonth = (TextView) findViewById(R.id.MeetingMonth);
        mMeetingYear = (TextView) findViewById(R.id.MeetingYear);
        mMeetingAgenda = (ListView) findViewById(R.id.MeetingAgendaListView);
        mMeetingToDo = (ListView) findViewById(R.id.MeetingTodoListView);
        mMeetingDetails = (TextView) findViewById(R.id.MeetingContactDetailsTextView);
        mMeetingDetails.setMovementMethod(new ScrollingMovementMethod());
        mMeetingresources = (RecyclerView) findViewById(R.id.MeetingResourcesRecyclerView);
        mRequestQueue = Volley.newRequestQueue(this);

        JSONRequest meetingRequest = new JSONRequest("http://162.243.18.170:9000/v1/meeting/get", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mMeeting = Meeting.createFromJSON(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setTitle(mMeeting.getmName());
                StringBuilder stringBuilder = new StringBuilder();
                if (mMeeting.getmTags() != null && mMeeting.getmTags().size() > 0) {
                    for (Tag g : mMeeting.getmTags())
                        stringBuilder.append(g.getTag() + ", ");
                    mMeetingTags.setText(stringBuilder.substring(0, stringBuilder.length() - 2).toString());
                }
                StringBuilder builder = new StringBuilder();
                builder.append(mMeeting.getmLocation());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(mMeeting.getmTimeZone()));
                calendar.setTime(mMeeting.getmDate());
                builder.append(" ").append(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(meeting.getmDate())).append("\n").append(mMeeting.getmTimeZone());
                mMeetingDay.setText(Integer.toString(calendar.get(Calendar.DATE)));
                mMeetingMonth.setText(new SimpleDateFormat("MMM", Locale.getDefault()).format(mMeeting.getmDate()));
                mMeetingYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
                mMeetingLocation.setText(builder.toString());
                if (mMeeting.getmAgenda() != null && mMeeting.getmAgenda().size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, mMeeting.getmAgenda());
                    mMeetingAgenda.setAdapter(adapter);
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, new String[]{"No agenta items are found."});
                    mMeetingAgenda.setAdapter(adapter);
                }
                if (mMeeting.getmToDo() != null && mMeeting.getmToDo().size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, mMeeting.getmToDo());
                    mMeetingToDo.setAdapter(adapter);
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, new String[]{"No to-do items are found."});
                    mMeetingToDo.setAdapter(adapter);
                }
                if (mMeeting.getmDetails() != null) {
                    stringBuilder = new StringBuilder();
                    ContactDetails contactDetails = mMeeting.getmDetails();
                    if (contactDetails.getmName() != null) {
                        stringBuilder.append(contactDetails.getmName()).append(" ");
                    }
                    if (contactDetails.getmSurname() != null) {
                        stringBuilder.append(contactDetails.getmSurname()).append(" ");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.append("\n");
                    if (contactDetails.getmMail() != null) {
                        stringBuilder.append(contactDetails.getmMail()).append(" ");
                    }
                    if (contactDetails.getmPhone() != null) {
                        stringBuilder.append(contactDetails.getmPhone()).append(" ");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    mMeetingDetails.setText(stringBuilder.toString());
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("authToken", App.mAuth);
                    jsonObject.accumulate("groupId", mMeeting.getmGroupID());
                    jsonObject.accumulate("meetingId", mMeeting.getmID());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONRequest jsonRequest = new JSONRequest("http://162.243.18.170:9000/v1/resource/queryResourcesByGroup", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResourceQuery resourceQuery = JSONBuilder.returnDefaultBuilder().create().fromJson(response, ResourceQuery.class);
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
                            LinearLayoutManager manager = new LinearLayoutManager(ViewMeetingActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            mMeetingresources.setLayoutManager(manager);
                            mMeetingresources.setAdapter(resourceListAdapter);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.width = resourceQuery.getResult().size() * 80;
                            mMeetingresources.setLayoutParams(params);
                        } else {
                            final List<String> list = Arrays.asList(new String[]{"No resources are found for this meeting."});
                            final swe574.boun.edu.androidproject.model.ArrayAdapter arrayAdapter = new swe574.boun.edu.androidproject.model.ArrayAdapter(list) {
                                @Override
                                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                    TextView viewGroup = (TextView) LayoutInflater.from(ViewMeetingActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
                                    return new ResourceViewHolder(viewGroup);
                                }

                                @Override
                                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                                    ((TextView) holder.itemView.findViewById(android.R.id.text1)).setText(list.get(0));
                                }
                            };
                            LinearLayoutManager manager = new LinearLayoutManager(ViewMeetingActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            mMeetingresources.setLayoutManager(manager);
                            mMeetingresources.setAdapter(arrayAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, jsonObject);
                mRequestQueue.add(jsonRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, requestObject);

        mRequestQueue.add(meetingRequest);
        mRequestQueue.start();

        Button mFindPeople = (Button) findViewById(R.id.meetingPeople);
        mFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewMeetingActivity.this, MeetingPeopleActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestQueue.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestQueue.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        mRequestQueue.stop();
    }
}
