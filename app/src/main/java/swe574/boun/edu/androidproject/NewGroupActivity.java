package swe574.boun.edu.androidproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import swe574.boun.edu.androidproject.R;

import static swe574.boun.edu.androidproject.R.id.buttonSave;


public class NewGroupActivity extends AppCompatActivity {
    private class CreateGroupTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
    }
    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCreateButton = (Button) findViewById(buttonSave);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(buttonSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
