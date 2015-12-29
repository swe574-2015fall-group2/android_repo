package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.ContactDetails;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.Tag;
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
    private ListView mMeetingAgenda;
    private ListView mMeetingToDo;
    private TextView mMeetingDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        Intent i = getIntent();
        mUser = i.getParcelableExtra("user");
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
                StringBuilder stringBuilder = new StringBuilder();
                for (Tag g : mMeeting.getmTags())
                    stringBuilder.append(g.getLabel() + ", ");
                mMeetingTags.setText(stringBuilder.substring(0, stringBuilder.length() - 2).toString());
                mMeetingLocation.setText(mMeeting.getmLocation());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mMeeting.getmDate());
                mMeetingDay.setText(calendar.get(Calendar.DATE));
                mMeetingMonth.setText(new SimpleDateFormat("MMM").format(mMeeting.getmDate()));
                mMeetingYear.setText(calendar.get(Calendar.YEAR));
                if(mMeeting.getmAgenda() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, mMeeting.getmAgenda());
                    mMeetingAgenda.setAdapter(adapter);
                }
                if(mMeeting.getmToDo() != null){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMeetingActivity.this, android.R.layout.simple_list_item_1, mMeeting.getmToDo());
                    mMeetingToDo.setAdapter(adapter);
                }
                if(mMeeting.getmDetails() != null){
                    stringBuilder = new StringBuilder();
                    ContactDetails contactDetails = mMeeting.getmDetails();
                    if(contactDetails.getmName() != null){
                        stringBuilder.append(contactDetails.getmName()).append(" ");
                    }
                    if(contactDetails.getmSurname() != null){
                        stringBuilder.append(contactDetails.getmSurname()).append(" ");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.append("\n");
                    if(contactDetails.getmMail() != null){
                        stringBuilder.append(contactDetails.getmMail()).append(" ");
                    }
                    if(contactDetails.getmPhone() != null){
                        stringBuilder.append(contactDetails.getmPhone()).append(" ");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    mMeetingDetails.setText(stringBuilder.toString());

                }
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
