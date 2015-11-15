package swe574.boun.edu.androidproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;

import static swe574.boun.edu.androidproject.R.id.buttonSave;


public class NewGroupActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mGroupNameView;
    private EditText mGroupDescriptionView;
    private EditText mGroupTagsView;
    private CreateGroupTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        mGroupNameView = (EditText) findViewById(R.id.groupName);
        mGroupDescriptionView = (EditText) findViewById(R.id.groupDesc);
        mGroupTagsView = (EditText) findViewById(R.id.groupTags);

        mCreateButton = (Button) findViewById(buttonSave);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();
            }
        });

    }

    private void attemptCreate() {
        if(mTask != null){
            return;
        }

        View view = null;
        String name,description,tags;
        if(!validateDescription(description = mGroupDescriptionView.getText().toString())){

        }
        if(!validateTags(tags = mGroupTagsView.getText().toString())){

        }
        if (!validateName(name = mGroupNameView.getText().toString())){
            
        }
        
    }

    private boolean validateName(String s) {
        return false;
    }

    private boolean validateTags(String s) {
        return false;
    }

    private boolean validateDescription(String s) {
        return false;
    }


    private class CreateGroupTask extends AsyncTask<Void, Void, Boolean> {
        private Group mGroup;
        private Context mContext;

        public CreateGroupTask(Group mGroup, Context mContext) {
            this.mGroup = mGroup;
            this.mContext = mContext;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                Toast.makeText(mContext, "The group has created successfully.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
