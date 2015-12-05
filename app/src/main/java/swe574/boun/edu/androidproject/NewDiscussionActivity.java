package swe574.boun.edu.androidproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import swe574.boun.edu.androidproject.tasks.CreateDiscussionTask;

public class NewDiscussionActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mDiscussionNameView;
    private EditText mDiscussionDescriptionView;
    private EditText mDiscussionTagsView;
    private CreateDiscussionTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);


        mDiscussionNameView = (EditText) findViewById(R.id.discussionName);
        mDiscussionDescriptionView = (EditText) findViewById(R.id.discussionDesc);
        mDiscussionTagsView = (EditText) findViewById(R.id.discussionTags);

        mCreateButton = (Button) findViewById(R.id.create_discussion);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();
            }
        });
    }

    private void attemptCreate() {
        if (mTask != null) {
            return;
        }

        View view = null;
        String name, description, tags;
        if (!validateDescription(description = mDiscussionDescriptionView.getText().toString())) {

        }
        if (!validateTags(tags = mDiscussionTagsView.getText().toString())) {

        }
        if (!validateName(name = mDiscussionNameView.getText().toString())) {

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

}
