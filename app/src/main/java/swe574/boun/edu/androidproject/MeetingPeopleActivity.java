package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import swe574.boun.edu.androidproject.model.User;

public class MeetingPeopleActivity extends AppCompatActivity {
    ArrayList<User> mPeople = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_people);
        mPeople = (ArrayList<User>) getIntent().getExtras().get("people");
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, R.layout.messagelistitem, mPeople){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(MeetingPeopleActivity.this).inflate(R.layout.messagelistitem, null, false);
                User user = mPeople.get(position);

                ImageView icon = (ImageView) viewGroup.findViewById(R.id.icon);
                if(user.getmImage() != null){
                    icon.setImageBitmap(user.getmImage().getmImage());
                }

                TextView nameView = (TextView) viewGroup.findViewById(R.id.name);
                String name = "", surname = "";
                if(user.getmName() != null || !TextUtils.isEmpty(user.getmName())){
                    name = user.getmName() + " ";
                }
                if(user.getmSurname() != null || !TextUtils.isEmpty(user.getmSurname())){
                    surname = user.getmSurname();
                }
                nameView.setText(name + surname);
                return viewGroup;
            }
        };
        ((ListView)findViewById(R.id.meetingPeople)).setAdapter(adapter);
    }
}
