package swe574.boun.edu.androidproject.requests;

import android.os.Bundle;
import android.os.Parcelable;

import com.android.volley.Response;
import com.google.gson.Gson;

import swe574.boun.edu.androidproject.model.SearchResult;
import swe574.boun.edu.androidproject.network.JSONBuilder;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class SearchResponseListener implements Response.Listener<String> {
    public static final String RESULT_TOKEN = "result";
    private OnTaskCompleted mListener;

    public SearchResponseListener(OnTaskCompleted mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(String response) {
        SearchResult searchResult;
        Gson gson = JSONBuilder.returnDefaultBuilder().create();
        searchResult = gson.fromJson(response, SearchResult.class);
        if(mListener != null){
            Bundle extras = new Bundle();
            extras.putParcelable(RESULT_TOKEN, searchResult);
            mListener.onTaskCompleted(extras);
        }
    }
}
