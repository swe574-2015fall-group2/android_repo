package swe574.boun.edu.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.tasks.FetchAllGroupsTask;
import swe574.boun.edu.androidproject.tasks.FetchMyGroupsTask;
import swe574.boun.edu.androidproject.tasks.FetchRecommendedGroupsTask;

/**
 * Created by Jongaros on 11/25/2015.
 */
public class ViewAllGroupsActivity extends AppCompatActivity {
    private User mUser;
    private ListView mGroups;
    private FetchAllGroupsTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_groups);
        setTitle("All Groups");
        //Initialize parameters
        mUser = getIntent().getParcelableExtra("user");
        mGroups = (ListView) findViewById(R.id.gridViewAllGroups);
        //Get Data from Database.
        mTask = new FetchAllGroupsTask((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0), mUser);
        mTask.execute();

        if (mGroups.getAdapter() == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_nogroups, R.id.textview, new String[]{"No groups are found. Click here to create a group."});
            mGroups.setAdapter(adapter);
            mGroups.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Intent i = new Intent(ViewAllGroupsActivity.this, NewGroupActivity.class);
                        startActivityForResult(i, HomeDrawerActivity.NEW_GROUP);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == HomeDrawerActivity.NEW_GROUP && resultCode == Activity.RESULT_OK){
            mTask = new FetchAllGroupsTask((ViewGroup) findViewById(android.R.id.content), mUser);
            mTask.execute();
        }
    }

}
