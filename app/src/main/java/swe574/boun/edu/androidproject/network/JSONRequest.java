package swe574.boun.edu.androidproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * Created by Jongaros on 12/26/2015.
 */
public class JSONRequest extends StringRequest {
    private final String mContentType;
    private JSONObject mRequestObject;

    public JSONRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, JSONObject request) {
        super(Method.POST, url, listener, errorListener);
        mContentType = "application/json";
        mRequestObject = request;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mRequestObject.toString().getBytes();
    }

    @Override
    public String getBodyContentType() {
        return mContentType;
    }
}
