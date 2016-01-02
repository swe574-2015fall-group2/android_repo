package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import swe574.boun.edu.androidproject.adapters.CommentListAdapter;
import swe574.boun.edu.androidproject.adapters.ListViewAdapterListener;
import swe574.boun.edu.androidproject.adapters.ResourceListAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Comment;
import swe574.boun.edu.androidproject.model.CommunicationType;
import swe574.boun.edu.androidproject.model.Discussion;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Note;
import swe574.boun.edu.androidproject.model.ResourceQuery;
import swe574.boun.edu.androidproject.model.Tag;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONBuilder;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

public class ViewCommunicateActivity extends AppCompatActivity {
    private static final int UPDATE_COMMUNICATE = 1;
    // Data Object
    private Discussion mDiscussion;
    private Note mNote;
    private Map<User, Comment> mCommentMap;
    private CommunicationType mCommunicationType;
    private String mURL;
    // Network Objects
    private RequestQueue mRequestQueue;
    private JSONRequest mViewCommunicationJSONRequest;
    private JSONRequest mGetResourcesJSONRequest;
    private JSONRequest mGetCommentJSONRequest;
    // UI Objects
    private TextView mCommunicationDescriptionTextView;
    private TextView mCommunicationTagsTextView;
    private RecyclerView mCommunicationResourcesRecyclerView;
    private ListView mCommunicationCommentsListView;
    private Button mCommunicationUpdateButton;
    private Group mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_communicate);
        Intent intent = getIntent();

        mGroup = intent.getParcelableExtra("group");

        mCommunicationDescriptionTextView = (TextView) findViewById(R.id.communicationDescriptionTextView);
        mCommunicationDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        mCommunicationTagsTextView = (TextView) findViewById(R.id.communicationTagsTextView);
        mCommunicationTagsTextView.setMovementMethod(new ScrollingMovementMethod());

        mCommunicationResourcesRecyclerView = (RecyclerView) findViewById(R.id.communicationResourcesRecyclerView);

        mCommunicationCommentsListView = (ListView) findViewById(R.id.communicationCommentsListView);

        mCommunicationType = (CommunicationType) intent.getSerializableExtra("type");
        String id, creator = "";
        if (mCommunicationType == CommunicationType.DISCUSSION) {
            mDiscussion = intent.getParcelableExtra("discussion");
            mURL = "http://162.243.18.170:9000/v1/discussion/query";
            id = mDiscussion.getId();
            creator = mDiscussion.getCreatorId();
        } else {
            mNote = intent.getParcelableExtra("note");
            mURL = "http://162.243.18.170:9000/v1/note/query";
            id = mNote.getId();
        }
        mCommunicationUpdateButton = (Button) findViewById(R.id.communicationUpdateButton);
        if (!App.mUserID.equals(creator)) {
            mCommunicationUpdateButton.setVisibility(View.GONE);
        }
        mCommunicationUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ViewCommunicateActivity.this, UpdateCommunicateActivity.class);
                newIntent.putExtra("type", mCommunicationType);
                if (mCommunicationType == CommunicationType.NOTE) {
                    newIntent.putExtra("note", mNote);
                } else {
                    newIntent.putExtra("discussion", mDiscussion);
                }
                newIntent.putExtra("group", mGroup);
                startActivityForResult(newIntent, UPDATE_COMMUNICATE);
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("authToken", App.mAuth);
            jsonObject.accumulate("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewCommunicationJSONRequest = new JSONRequest(mURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final Gson gson = JSONBuilder.returnDefaultBuilder().create();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    jsonObject = jsonObject.getJSONObject("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject requestJson = new JSONObject();
                try {
                    requestJson.accumulate("authToken", App.mAuth);
                    requestJson.accumulate("groupId", mGroup.getmID());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mCommunicationType == CommunicationType.NOTE) {
                    try {
                        requestJson.accumulate("noteId", mNote.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mNote = gson.fromJson(jsonObject.toString(), Note.class);
                    setTitle(mNote.getTitle());
                    mCommunicationDescriptionTextView.setText(mNote.getText());
                    mCommunicationDescriptionTextView.setMaxLines(10);
                    String tagText = "";
                    for (Tag t : mNote.getTagList()) {
                        tagText += "#" + t.getTag() + ", ";
                    }
                    if (!tagText.equals("") && tagText.length() >= 2) {
                        mCommunicationTagsTextView.setText(tagText.substring(0, tagText.length() - 2));
                    }
                    mCommunicationTagsTextView.setMaxLines(5);
                    ViewGroup view = (ViewGroup) findViewById(R.id.layout);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                    params.weight = 1.75f;
                    params.height = 0;
                    view.setLayoutParams(params);
                    mCommunicationCommentsListView.setVisibility(View.GONE);
                } else {
                    try {
                        requestJson.accumulate("discussionId", mDiscussion.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDiscussion = gson.fromJson(jsonObject.toString(), Discussion.class);
                    setTitle(mDiscussion.getName());
                    mCommunicationDescriptionTextView.setText(mDiscussion.getDescription());
                    String tagText = "";
                    for (Tag t : mDiscussion.getTagList()) {
                        tagText += "#" + t.getTag() + ", ";
                    }
                    if (!tagText.equals("") && tagText.length() >= 2) {
                        mCommunicationTagsTextView.setText(tagText.substring(0, tagText.length() - 2));
                    }
                    if (mDiscussion.getCommentList() != null) {
                        mCommentMap = new LinkedHashMap<>();
                        final int[] requests = {mDiscussion.getCommentList().size()};
                        for (final Comment comment : mDiscussion.getCommentList()) {
                            jsonObject = new JSONObject();
                            try {
                                jsonObject.accumulate("authToken", App.mAuth);
                                jsonObject.accumulate("id", comment.getCreatorId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mGetCommentJSONRequest = new JSONRequest("http://162.243.18.170:9000/v1/user/get", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject result = new JSONObject(response);
                                        result = result.getJSONObject("result");
                                        User user = User.createFromJSON(comment.getCreatorId(), result);
                                        mCommentMap.put(user, comment);
                                        requests[0]--;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }, jsonObject);
                            mRequestQueue.add(mGetCommentJSONRequest);
                        }
                        WaitOnUITask task = new WaitOnUITask(new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(Bundle extras) {
                                CommentListAdapter commentListAdapter = new CommentListAdapter(null, mCommentMap, ViewCommunicateActivity.this);
                                mCommunicationCommentsListView.setAdapter(commentListAdapter);
                            }
                        }, requests);
                        task.execute();
                    }
                }
                mGetResourcesJSONRequest = new JSONRequest("http://162.243.18.170:9000/v1/resource/queryResourcesByGroup", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResourceQuery resourceQuery = gson.fromJson(response, ResourceQuery.class);
                        if (resourceQuery.getResult() != null) {
                            ResourceListAdapter resourceListAdapter = new ResourceListAdapter(resourceQuery.getResult(), new ListViewAdapterListener() {
                                @Override
                                public void onViewCreated(ViewGroup viewGroup) {
                                    viewGroup.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });
                                }
                            });
                            LinearLayoutManager manager = new LinearLayoutManager(ViewCommunicateActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            mCommunicationResourcesRecyclerView.setLayoutManager(manager);
                            mCommunicationResourcesRecyclerView.setAdapter(resourceListAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, requestJson);
                mRequestQueue.add(mGetResourcesJSONRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, jsonObject);
        mRequestQueue.add(mViewCommunicationJSONRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_COMMUNICATE && resultCode == RESULT_OK) {
            mRequestQueue.add(mViewCommunicationJSONRequest);
        }
    }

    private class WaitOnUITask extends AsyncTask<Void, Void, Void> {
        OnTaskCompleted mListener;
        int requests[];

        public WaitOnUITask(OnTaskCompleted mListener, int[] requests) {
            this.mListener = mListener;
            this.requests = requests;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (requests[0] > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            mListener.onTaskCompleted(null);
        }
    }


}
