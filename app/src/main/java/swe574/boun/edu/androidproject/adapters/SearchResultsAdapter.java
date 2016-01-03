package swe574.boun.edu.androidproject.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.GroupTabbedActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewCommunicateActivity;
import swe574.boun.edu.androidproject.ViewMeetingActivity;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Discussion;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.Note;
import swe574.boun.edu.androidproject.model.SearchResult;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.requests.GenericErrorListener;
import swe574.boun.edu.androidproject.requests.GetGroupResponseListener;
import swe574.boun.edu.androidproject.requests.GetMeetingResponseListener;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {
    private User mUser;
    private SearchResult mResult;

    public SearchResultsAdapter(SearchResult mResult, User mUser) {
        this.mResult = mResult;
        this.mUser = mUser;
    }

    @Override
    public SearchResultsAdapter.SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchResultsViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(final SearchResultsAdapter.SearchResultsViewHolder holder, int position) {
        final SearchResult.SearchDetail result = mResult.getResultList().get(position);
        holder.mEntityTypeTextView.setText(result.getType().toString());
        holder.mIdTextView.setText(result.getId());
        holder.mDescriptionTextView.setText(result.getDescription());
        // No SQL injection here ^^
        holder.mTagTextView.setText("#" + result.getTag().getTag() + " (" + result.getTag().getClazz() + ")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putParcelable("user", mUser);
                switch (result.getType()) {
                    case GROUP:
                        JSONObject requestObject = new JSONObject();
                        try {
                            requestObject.accumulate("authToken", App.mAuth);
                            requestObject.accumulate("id", result.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONRequest request = new JSONRequest("http://162.243.18.170:9000/v1/group/query", new GetGroupResponseListener(new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(Bundle extras) {
                                Group group = extras.getParcelable(GetGroupResponseListener.GROUP_TOKEN);
                                bundle.putParcelable("group", group);
                            }
                        }), new GenericErrorListener(requestObject, null), requestObject);
                        Volley.newRequestQueue(holder.itemView.getContext()).add(request);
                        Intent intent = new Intent(holder.itemView.getContext(), GroupTabbedActivity.class);
                        intent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case DISCUSSION:
                        bundle.putParcelable("group", new Group(null, null, null, "0", null, false, null));
                        Discussion discussion = new Discussion();
                        discussion.setId(result.getId());
                        bundle.putParcelable("discussion", discussion);
                        Intent dIntent = new Intent(holder.itemView.getContext(), ViewCommunicateActivity.class);
                        dIntent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(dIntent);
                        break;
                    case NOTE:
                        bundle.putParcelable("group", new Group(null, null, null, "0", null, false, null));
                        Note note = new Note();
                        note.setId(result.getId());
                        bundle.putParcelable("note", note);
                        Intent nIntent = new Intent(holder.itemView.getContext(), ViewCommunicateActivity.class);
                        nIntent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(nIntent);
                        break;
                    case MEETING:
                        JSONObject requestMeetingObject = new JSONObject();
                        try {
                            requestMeetingObject.accumulate("authToken", App.mAuth);
                            requestMeetingObject.accumulate("id", result.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONRequest meetingRequest = new JSONRequest("http://162.243.18.170:9000/v1/meeting/get", new GetMeetingResponseListener(new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(Bundle extras) {
                                Meeting meeting = extras.getParcelable(GetMeetingResponseListener.MEETING_TOKEN);
                                bundle.putParcelable("meeting", meeting);
                            }
                        }), new GenericErrorListener(requestMeetingObject, null), requestMeetingObject);
                        Volley.newRequestQueue(holder.itemView.getContext()).add(meetingRequest);
                        Intent mIntent = new Intent(holder.itemView.getContext(), ViewMeetingActivity.class);
                        mIntent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(mIntent);
                        break;
                    case RESOURCE:
                        Toast.makeText(holder.itemView.getContext(), "This feature is currently in development", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.getResultList().size();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView mEntityTypeTextView;
        public TextView mIdTextView;
        public TextView mDescriptionTextView;
        public TextView mTagTextView;

        public SearchResultsViewHolder(View itemView) {
            super(itemView);
            mEntityTypeTextView = (TextView) itemView.findViewById(R.id.entityTypeTextView);
            mIdTextView = (TextView) itemView.findViewById(R.id.idTextView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            mTagTextView = (TextView) itemView.findViewById(R.id.tagTextView);
        }
    }
}
