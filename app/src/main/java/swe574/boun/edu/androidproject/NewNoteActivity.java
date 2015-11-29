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

public class NewNoteActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mNoteNameView;
    private EditText mNoteDescriptionView;
    private EditText mNoteTagsView;
    private CreateNoteTask mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        mNoteNameView = (EditText) findViewById(R.id.noteName);
        mNoteDescriptionView = (EditText) findViewById(R.id.noteDesc);
        mNoteTagsView = (EditText) findViewById(R.id.noteTags);

        mCreateButton = (Button) findViewById(R.id.create_note);
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
        if (!validateDescription(description = mNoteDescriptionView.getText().toString())) {

        }
        if (!validateTags(tags = mNoteTagsView.getText().toString())) {

        }
        if (!validateName(name = mNoteNameView.getText().toString())) {

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


    private class CreateNoteTask extends AsyncTask<Void, Void, Boolean> {
        private ContactsContract.CommonDataKinds.Note mNote;
        private Context mContext;

        public CreateNoteTask(ContactsContract.CommonDataKinds.Note mNote, Context mContext) {
            this.mNote = mNote;
            this.mContext = mContext;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(mContext, "The note has created successfully.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
