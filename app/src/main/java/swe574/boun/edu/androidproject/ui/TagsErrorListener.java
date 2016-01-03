package swe574.boun.edu.androidproject.ui;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Jongaros on 12/31/2015.
 */
public class TagsErrorListener implements Response.ErrorListener {
    private JSONObject mJSONObject;

    public TagsErrorListener(JSONObject mJSONObject) {
        this.mJSONObject = mJSONObject;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("ERROR/REQUEST", mJSONObject.toString());
        if(error.networkResponse != null)
        Log.e("ERROR/RESPONSE", new String(error.networkResponse.data));
        else{
            Log.e("ERROR/RESPONSE", "No Response");
        }
    }
}
