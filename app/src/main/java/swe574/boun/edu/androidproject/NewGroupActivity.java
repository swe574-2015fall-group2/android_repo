package swe574.boun.edu.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.User;
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
        if (!validateDescription(mGroupDescriptionView.getText().toString())) {
            focus = mGroupDescriptionView;
            cancel = true;
        }
        if (!validateName(mGroupNameView.getText().toString())) {
            focus = mGroupNameView;
            cancel = true;
        }

        if (!cancel) {
            mTask = new CreateGroupTask(this, (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));
            mTask.execute((Void) null);
        } else {
            focus.requestFocus();
        }
    }

    private boolean validateName(String s) {
        return s.length() > 4;
    }


    private boolean validateDescription(String s) {
        return s.length() > 10;
    }

}
