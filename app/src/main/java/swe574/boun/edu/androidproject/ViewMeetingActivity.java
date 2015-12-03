package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import swe574.boun.edu.androidproject.model.Meeting;

public class ViewMeetingActivity extends AppCompatActivity {

    private TextView mMeetingTags;
    private TextView mMeetingLocation;
    private TextView mMeetingDay;
    private TextView mMeetingMonth;
    private TextView mMeetingYear;
    private ListView mMeetingAgenda;
    private ListView mMeetingToDo;
    private String mAuth;
    private Meeting mMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        Intent i = getIntent();
        mAuth = i.getStringExtra("user");
        mMeeting = i.getParcelableExtra("meeting");

        mMeetingTags = (TextView) findViewById(R.id.meetingTags);
        mMeetingTags.setVisibility(View.GONE);

        mMeetingLocation = (TextView) findViewById(R.id.MeetingLocation);
        mMeetingLocation.setText(mMeeting.getmLocation());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mMeeting.getmDate());

        mMeetingDay = (TextView) findViewById(R.id.MeetingDay);
        mMeetingDay.setText(calendar.get(Calendar.DATE));

        mMeetingMonth = (TextView) findViewById(R.id.MeetingMonth);
        mMeetingMonth.setText(DateFormatSymbols.getInstance().getMonths()[calendar.get(Calendar.MONTH)]);

        mMeetingYear = (TextView) findViewById(R.id.MeetingYear);
        mMeetingYear.setText(calendar.get(Calendar.YEAR));

        mMeetingAgenda = (ListView) findViewById(R.id.meetingAgenda);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMeeting.getmAgenda());
        mMeetingAgenda.setAdapter(adapter);

        mMeetingToDo = (ListView) findViewById(R.id.meetingToDo);
        ArrayAdapter<String> tdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMeeting.getmToDo());
        mMeetingToDo.setAdapter(tdAdapter);

        Button mFindPeople = (Button) findViewById(R.id.meetingPeople);
        mFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewMeetingActivity.this, MeetingPeopleActivity.class);
                i.putExtra("meeting", mMeeting);
                startActivity(i);
            }
        });
    }

}
