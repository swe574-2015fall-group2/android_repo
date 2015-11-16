package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewMeetingActivity extends AppCompatActivity {

    private ViewMeetingTask mMeetingTask;
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

        Button mFindPeople = (Button) findViewById(R.id.meetingPeople);
        mFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewMeetingActivity.this, MeetingPeopleActivity.class);
                startActivity(i);
            }
        });

        mMeetingTask = null;
    }

    private class ViewMeetingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mMeetingAgenda = (TextView) findViewById(R.id.meetingAgenda);
            mMeetingDay = (TextView) findViewById(R.id.MeetingDay);
            mMeetingLocation = (TextView) findViewById(R.id.MeetingLocation);
            mMeetingMonth = (TextView) findViewById(R.id.MeetingMonth);
            mMeetingTags = (TextView) findViewById(R.id.meetingTags);
            mMeetingToDo = (TextView) findViewById(R.id.meetingToDo);
            mMeetingYear = (TextView) findViewById(R.id.MeetingYear);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //TODO Implement webservice
            return null;
        }
    }
}
