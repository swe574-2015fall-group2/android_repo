package swe574.boun.edu.androidproject.requests;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class GetMeetingResponseListener implements Response.Listener<String>{
    public static final String MEETING_TOKEN = "meeting";
    @NonNull
    private OnTaskCompleted mListener;

    public GetMeetingResponseListener(@NonNull OnTaskCompleted mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(String response) {
        Meeting meeting = null;
        try {
            meeting = Meeting.createFromJSON(new JSONObject(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEETING_TOKEN, meeting);
        mListener.onTaskCompleted(bundle);
    }
}
