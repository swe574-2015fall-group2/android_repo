package swe574.boun.edu.androidproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;

import swe574.boun.edu.androidproject.tasks.CreateGroupTask;
import swe574.boun.edu.androidproject.ui.TagData;
import swe574.boun.edu.androidproject.ui.TagsCompletionView;


public class NewGroupActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener{
    private Button mCreateButton;
    private EditText mGroupNameView;
    private EditText mGroupDescriptionView;
    private TagsCompletionView mTagsCompletionView;
    private FilteredArrayAdapter<TagData> mAdapter;
    private CreateGroupTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setTitle("New Group");

        mGroupNameView = (EditText) findViewById(R.id.groupName);
        mGroupDescriptionView = (EditText) findViewById(R.id.groupDesc);

        mTagsCompletionView = (TagsCompletionView) findViewById(R.id.groupTags);
        mAdapter = new FilteredArrayAdapter<TagData>(this, R.layout.tag_layout, new ArrayList<TagData>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.tag_layout, parent, false);
                }

                TagData tagData = getItem(position);
                ((TextView)convertView.findViewById(R.id.label)).setText(tagData.getmLabel());
                ((TextView)convertView.findViewById(R.id.description)).setText(tagData.getmDescription());

                return convertView;
            }

            @Override
            protected boolean keepObject(TagData obj, String mask) {
                return true;
            }
        };
        mTagsCompletionView.setAdapter(mAdapter);
        mTagsCompletionView.setTokenListener(this);
        mTagsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RequestQueue requestQueue = Volley.newRequestQueue(NewGroupActivity.this);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mTagsCompletionView.addTextChangedListener(textWatcher);

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

    @Override
    public void onTokenAdded(Object token) {

    }

    @Override
    public void onTokenRemoved(Object token) {

    }
}
