package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import swe574.boun.edu.androidproject.tasks.CreateGroupTask;


public class NewGroupActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mGroupNameView;
    private EditText mGroupDescriptionView;
    private CreateGroupTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setTitle("New Group");

        mGroupNameView = (EditText) findViewById(R.id.groupName);
        mGroupDescriptionView = (EditText) findViewById(R.id.groupDesc);

        mCreateButton = (Button) findViewById(R.id.create_group);
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

        boolean cancel = false;
        View focus = null;
        String name, description;
        if (!validateDescription(description = mGroupDescriptionView.getText().toString())) {
            focus = mGroupDescriptionView;
            mGroupDescriptionView.setError("Group Description cannot be empty");
            cancel = true;
        }
        if (!validateName(name = mGroupNameView.getText().toString())) {
            focus = mGroupNameView;
            mGroupNameView.setError("Group Name cannot be empty");
            cancel = true;
        }

        if (!cancel) {
            // TODO IMPLEMENT TAGS
            mTask = new CreateGroupTask(this, (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0), null);
            mTask.execute((Void) null);
        } else {
            focus.requestFocus();
        }
    }

    private boolean validateName(String s) {
        return !TextUtils.isEmpty(s);
    }


    private boolean validateDescription(String s) {
        return !TextUtils.isEmpty(s);
    }

}
