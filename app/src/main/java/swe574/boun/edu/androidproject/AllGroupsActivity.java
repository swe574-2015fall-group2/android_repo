package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import swe574.boun.edu.androidproject.tasks.FetchAllGroupsTask;

/**
 * Created by Jongaros on 11/25/2015.
 */
public class AllGroupsActivity extends AppCompatActivity{
    private String mAuth;
    private GridView mGroups;
    private FetchAllGroupsTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_groups);
        //Initialize parameters
        mAuth = getIntent().getStringExtra("user");
        mGroups = (GridView) findViewById(R.id.gridViewAllGroups);
        //Get Data from Database.
        mTask = new FetchAllGroupsTask((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0),mAuth);
        mTask.execute();

    }

}
