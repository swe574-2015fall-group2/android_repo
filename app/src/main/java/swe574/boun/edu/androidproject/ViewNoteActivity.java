package swe574.boun.edu.androidproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewNoteActivity extends AppCompatActivity {

    private ViewNoteTask mNoteTask;
    private TextView mNoteTags;
    private TextView mNoteName;
    private TextView mNoteDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);


        mNoteTask = null;
    }

    private class ViewNoteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mNoteName = (TextView) findViewById(R.id.noteName);
            mNoteDesc = (TextView) findViewById(R.id.noteDesc);
            mNoteTags = (TextView) findViewById(R.id.noteTags);

        }

        @Override
        protected Void doInBackground(Void... params) {
            //TODO Implement webservice
            return null;
        }
    }
}
