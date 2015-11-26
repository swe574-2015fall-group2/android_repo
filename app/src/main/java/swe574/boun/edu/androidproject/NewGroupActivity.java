package swe574.boun.edu.androidproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.tasks.CreateGroupTask;


public class NewGroupActivity extends AppCompatActivity {
    private Button mCreateButton;
    private String mAuth;
    private EditText mGroupNameView;
    private EditText mGroupDescriptionView;
    private CreateGroupTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        mAuth = getIntent().getStringExtra("user");

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
        if(mTask != null){
            return;
        }

        boolean cancel = false;
        View focus = null;
        String name,description;
        if(!validateDescription(description = mGroupDescriptionView.getText().toString())){
            focus = mGroupDescriptionView;
            cancel = true;
        }
        if (!validateName(name = mGroupNameView.getText().toString())){
            focus = mGroupNameView;
            cancel = true;
        }

        if(!cancel){
            mTask = new CreateGroupTask(this, this, mAuth, (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));
            mTask.execute((Void) null);
        }
        else {
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
