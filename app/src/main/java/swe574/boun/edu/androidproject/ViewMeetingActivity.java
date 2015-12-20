package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.User;

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
        mMeeting = i.getParcelableExtra("meeting");

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
