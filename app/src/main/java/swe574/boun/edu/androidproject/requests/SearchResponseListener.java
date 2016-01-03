package swe574.boun.edu.androidproject.requests;

import com.android.volley.Response;

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
        if(mListener != null){
            mListener.onTaskCompleted(null);
        }
    }
}
