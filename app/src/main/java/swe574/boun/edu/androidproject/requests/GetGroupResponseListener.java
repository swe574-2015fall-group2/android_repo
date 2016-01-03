package swe574.boun.edu.androidproject.requests;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class GetGroupResponseListener implements Response.Listener<String>{
    public static final String GROUP_TOKEN = "group";
    @NonNull
    OnTaskCompleted mListener;

    public GetGroupResponseListener(OnTaskCompleted mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(String response) {
        Bundle bundle = new Bundle();
        try {
            Group group = Group.fromJsonString(new JSONObject(response));
            bundle.putParcelable(GROUP_TOKEN, group);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mListener.onTaskCompleted(bundle);
    }
}
