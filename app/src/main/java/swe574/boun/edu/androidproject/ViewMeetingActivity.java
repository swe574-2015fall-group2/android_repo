package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONRequest;

public class ViewMeetingActivity extends AppCompatActivity {
    private User mUser;
    private Meeting mMeeting;
    private TextView mMeetingTags;
    private TextView mMeetingLocation;
    private TextView mMeetingDay;
    private TextView mMeetingMonth;
    private TextView mMeetingYear;
    private TextView mMeetingAgenda;
    private TextView mMeetingToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        Intent i = getIntent();
        mUser = i.getParcelableExtra("user");
        Meeting meeting = i.getParcelableExtra("meeting");
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.accumulate("authToken", App.mAuth);
            requestObject.accumulate("id", meeting.getmID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONRequest meetingRequest = new JSONRequest("http://162.243.215.160:9000/v1/meeting/get", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mMeeting = Meeting.createFromJSON(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setTitle(mMeeting.getmName());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, requestObject);

        requestQueue.add(meetingRequest);

        Button mFindPeople = (Button) findViewById(R.id.meetingPeople);
        mFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewMeetingActivity.this, MeetingPeopleActivity.class);
                startActivity(i);
            }
        });


    }
}
