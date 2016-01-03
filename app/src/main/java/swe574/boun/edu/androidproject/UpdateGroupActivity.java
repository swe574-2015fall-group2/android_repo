package swe574.boun.edu.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.network.JSONBuilder;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.RequestQueueBuilder;
import swe574.boun.edu.androidproject.ui.TagData;
import swe574.boun.edu.androidproject.ui.TagsCompletionView;


public class UpdateGroupActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener {
    private Group mGroup;
    private Button mCreateButton;
    private EditText mGroupNameView;
    private EditText mGroupDescriptionView;
    private TagsCompletionView mTagsCompletionView;
    private FilteredArrayAdapter<TagData> mAdapter;
    private List<Tag> mTags;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setTitle("Update Group");

        Intent intent = getIntent();
        mGroup = intent.getParcelableExtra("group");

        mGroupNameView = (EditText) findViewById(R.id.groupName);
        mGroupNameView.setMovementMethod(new ScrollingMovementMethod());
        mGroupNameView.setText(mGroup.getmName());
        mGroupDescriptionView = (EditText) findViewById(R.id.groupDesc);
        mGroupDescriptionView.setMovementMethod(new ScrollingMovementMethod());
        mGroupDescriptionView.setText(mGroup.getmDescription());

        mTagsCompletionView = (TagsCompletionView) findViewById(R.id.groupTags);
        mTagsCompletionView.setMovementMethod(new ScrollingMovementMethod());
        for (int i = 0; i < mGroup.getmTags().size(); i++) {
            mTagsCompletionView.addObject(TagData.fromTag(mGroup.getmTags().get(i)));
        }
        mTags = mGroup.getmTags();
        mAdapter = new FilteredArrayAdapter<TagData>(this, R.layout.tag_layout, new ArrayList<TagData>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.tag_layout, parent, false);
                }

                final TagData tagData = getItem(position);
                ((TextView) convertView.findViewById(R.id.label)).setText(tagData.getmLabel());
                if (tagData.getmDescription() == null || tagData.getmDescription().equals("null")) {
                    ((TextView) convertView.findViewById(R.id.description)).setText("");
                } else {
                    ((TextView) convertView.findViewById(R.id.description)).setText(tagData.getmDescription());
                }

                return convertView;
            }

            @Override
            protected boolean keepObject(TagData obj, String mask) {
                return true;
            }

        };
        mRequestQueue = RequestQueueBuilder.preapareSerialQueue(this);
        mRequestQueue.start();
        final String url = "http://162.243.18.170:9000/v1/semantic/queryLabel";

        mTagsCompletionView.setAdapter(mAdapter);
        mTagsCompletionView.setTokenListener(this);
        mTagsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        TextWatcher textWatcher = new TextWatcher() {
            int counter;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String[] splitted = s.toString().split(",");
                String tag = splitted[splitted.length - 1];
                counter = (splitted.length) / 3;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String[] splitted = s.toString().split(",");
                String tag = splitted[splitted.length - 1];
                if (tag.length() < 2) {
                    return;
                }

                final JSONObject object = new JSONObject();
                try {
                    object.accumulate("authToken", App.mAuth);
                    object.accumulate("queryString", tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<TagData> tagList = new ArrayList<>();
                        Log.v("RESPONSE", response);
                        try {
                            JSONObject result = new JSONObject(response);
                            if (result.has("result")) {
                                result = result.getJSONObject("result");
                            } else {
                                return;
                            }
                            // FIXME HACK DUE TO SERVICE INCONSISTENCY
                            Gson gson = JSONBuilder.returnDefaultBuilder().create();
                            JSONArray array = result.getJSONArray("dataList");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject tagObject = array.getJSONObject(i);
                                tagList.add(gson.fromJson(tagObject.toString(), TagData.class));
                            }
                            mAdapter.clear();
                            mAdapter.addAll(tagList);
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return object.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
                mRequestQueue.add(request);
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
        mCreateButton.setText("Update Group");
    }

    private void attemptCreate() {
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
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("authToken", App.mAuth);
                jsonObject.accumulate("name", mGroupNameView.getText().toString());
                jsonObject.accumulate("description", mGroupDescriptionView.getText().toString());
                jsonObject.accumulate("groupId", mGroup.getmID());
                JSONArray jsonArray = new JSONArray();
                for (Tag t : mTags) {
                    jsonArray.put(t.toJson());
                }
                jsonObject.accumulate("tagList", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONRequest jsonRequest = new JSONRequest("http://162.243.18.170:9000/v1/group/update", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(UpdateGroupActivity.this, "You have successfully updated your group.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Update Group", jsonObject.toString());
                    NetworkResponse response = error.networkResponse;
                    if (response.data != null && response.data.length > 0) {
                        try {
                            JSONObject object = new JSONObject(new String(response.data));
                            if (object.has("status")) {
                                if (object.getString("status").equals("success")) {
                                    Toast.makeText(UpdateGroupActivity.this, "You have successfully updated your group.", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, jsonObject);
            mRequestQueue.add(jsonRequest);
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
        TagData tagData = (TagData) token;
        mTags.add(tagData.toTag());
    }

    @Override
    public void onTokenRemoved(Object token) {
        TagData tagData = (TagData) token;
        mTags.remove(tagData.toTag());
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
