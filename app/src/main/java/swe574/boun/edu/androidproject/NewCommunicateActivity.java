package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.CommunicationType;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.RequestQueueBuilder;
import swe574.boun.edu.androidproject.ui.TagData;
import swe574.boun.edu.androidproject.ui.TagsArrayAdapter;
import swe574.boun.edu.androidproject.ui.TagsCompletionView;
import swe574.boun.edu.androidproject.ui.TagsErrorListener;
import swe574.boun.edu.androidproject.ui.TagsResponseListener;
import swe574.boun.edu.androidproject.ui.TokenTextWatcher;

public class NewCommunicateActivity extends AppCompatActivity implements View.OnClickListener {
    // UI Elements
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private List<Tag> mTagList;
    private CommunicationType mCommunicationType;
    private Button mCreateButton;
    private TagsArrayAdapter mTagsArrayAdapter;
    // Network Elements
    private String mUrl;
    private RequestQueue mRequestQueue;
    private JSONRequest mTagsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communucate);

        mTitleEditText = (EditText) findViewById(R.id.communicationTitleEditText);
        mTitleEditText.setMovementMethod(new ScrollingMovementMethod());

        mDescriptionEditText = (EditText) findViewById(R.id.communicationDescriptionEditText);
        mDescriptionEditText.setMovementMethod(new ScrollingMovementMethod());

        TagsCompletionView mTagsCompletionView = (TagsCompletionView) findViewById(R.id.communicationTagsTagsCompletionView);
        mTagsCompletionView.setMovementMethod(new ScrollingMovementMethod());

        mTagList = new ArrayList<>();
        mTagsArrayAdapter = new TagsArrayAdapter(this, R.layout.tag_layout, new ArrayList<TagData>());
        mRequestQueue = RequestQueueBuilder.preapareSerialQueue(this);
        mRequestQueue.start();

        TokenTextWatcher tokenTextWatcher = new TokenTextWatcher(mRequestQueue) {
            @Override
            public void onTextChanged(String tag) {
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("authToken", App.mAuth);
                    jsonObject.accumulate("queryString", tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTagsRequest = new JSONRequest("http://162.243.18.170:9000/v1/semantic/queryLabel", new TagsResponseListener(mTagsArrayAdapter), new TagsErrorListener(jsonObject), jsonObject);
                mRequestQueue.add(mTagsRequest);
            }
        };

        mTagsCompletionView.configurate(mTagsArrayAdapter, new TokenCompleteTextView.TokenListener() {
            @Override
            public void onTokenAdded(Object token) {
                TagData data = (TagData) token;
                mTagList.add(data.toTag());
            }

            @Override
            public void onTokenRemoved(Object token) {
                TagData data = (TagData) token;
                mTagList.remove(data.toTag());
            }
        }, tokenTextWatcher);

        Intent intent = getIntent();
        mCreateButton = (Button) findViewById(R.id.createButton);
        mCreateButton.setOnClickListener(this);
        mCommunicationType = (CommunicationType) intent.getSerializableExtra("type");
        if (mCommunicationType == CommunicationType.NOTE) {
            mUrl = "http://162.243.18.170:9000/v1/note/create";
            setTitle("New Note");
            mCreateButton.setText("Create Note");
        } else {
            mUrl = "http://162.243.18.170:9000/v1/discussion/create";
            setTitle("New Discussion");
            mCreateButton.setText("Create Discussion");
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mCreateButton) {
            boolean cancel = false;
            View focus = null;

            if (!validateDescription(mDescriptionEditText.getText().toString())) {
                focus = mDescriptionEditText;
                mDescriptionEditText.setError("Description cannot be empty");
                cancel = true;
            }
            if (!validateName(mTitleEditText.getText().toString())) {
                focus = mTitleEditText;
                mTitleEditText.setError("Name cannot be empty");
                cancel = true;
            }

            if (!cancel) {
                final JSONObject jsonObject = new JSONObject();
                if (mCommunicationType == CommunicationType.NOTE) {
                    try {
                        jsonObject.accumulate("authToken", App.mAuth);
                        jsonObject.accumulate("title", mTitleEditText.getText().toString());
                        jsonObject.accumulate("text", mDescriptionEditText.getText().toString());
                        jsonObject.accumulate("groupId", ((Group) getIntent().getParcelableExtra("group")).getmID());
                        JSONArray array = new JSONArray();
                        for (Tag tag : mTagList) {
                            array.put(tag.toJson());
                        }
                        jsonObject.accumulate("tagList", array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        jsonObject.accumulate("authToken", App.mAuth);
                        jsonObject.accumulate("name", mTitleEditText.getText().toString());
                        jsonObject.accumulate("description", mDescriptionEditText.getText().toString());
                        jsonObject.accumulate("groupId", ((Group) getIntent().getParcelableExtra("group")).getmID());
                        JSONArray array = new JSONArray();
                        for (Tag tag : mTagList) {
                            array.put(tag.toJson());
                        }
                        jsonObject.accumulate("tagList", array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONRequest mCreateRequest = new JSONRequest(mUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NewCommunicateActivity.this, "You have successfully created " + (mCommunicationType == CommunicationType.NOTE ? "Note" : "Discussion"), Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR/REQUEST", jsonObject.toString());
                        Log.e("ERROR/RESPONSE", new String(error.networkResponse.data));
                    }
                }, jsonObject);
                mRequestQueue.add(mCreateRequest);
            } else {
                focus.requestFocus();
            }
        }
    }


    private boolean validateName(String s) {
        return !TextUtils.isEmpty(s);
    }


    private boolean validateDescription(String s) {
        return !TextUtils.isEmpty(s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestQueue.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestQueue.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        mRequestQueue.stop();
    }
}

