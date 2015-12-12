package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewDiscussionActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mDiscussionNameView;
    private EditText mDiscussionDescriptionView;
    private EditText mDiscussionTagsView;

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
