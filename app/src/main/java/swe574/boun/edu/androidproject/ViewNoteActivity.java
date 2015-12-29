package swe574.boun.edu.androidproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import swe574.boun.edu.androidproject.ui.TagsCompletionView;

public class ViewNoteActivity extends AppCompatActivity {

    private EditText mNoteTitleEditText;
    private TagsCompletionView mNoteTagsTagsCompletionView;
    private EditText mNoteTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);


    }

}
