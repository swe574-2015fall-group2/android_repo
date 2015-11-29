package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ListView;

import swe574.boun.edu.androidproject.tasks.FetchAllGroupsTask;

/**
 * Created by Jongaros on 11/25/2015.
 */
public class ViewAllGroupsActivity extends AppCompatActivity {
    private String mAuth;
    private ListView mGroups;
    private FetchAllGroupsTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_groups);
        //Initialize parameters
        mAuth = getIntent().getStringExtra("user");
        mGroups = (ListView) findViewById(R.id.gridViewAllGroups);
        //Get Data from Database.
        mTask = new FetchAllGroupsTask((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0), mAuth);
        mTask.execute();
        setTitle("All Groups");

    }

}
