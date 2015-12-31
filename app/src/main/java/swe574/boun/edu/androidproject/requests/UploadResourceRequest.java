package swe574.boun.edu.androidproject.requests;

import com.android.volley.Response;

import org.json.JSONObject;

import swe574.boun.edu.androidproject.model.ResourceType;
import swe574.boun.edu.androidproject.network.JSONRequest;

/**
 * Created by Jongaros on 12/30/2015.
 */
public class UploadResourceRequest extends JSONRequest {
    public UploadResourceRequest(ResourceType type, Response.Listener<String> listener, Response.ErrorListener errorListener, JSONObject object) {
        super(getResourceURL(type), listener, errorListener, object);
    }

    private static String getResourceURL(ResourceType type) {
        String url = null;
        if (type == ResourceType.INTERNAL) {
            url = "http://162.243.18.170:9000/v1/resource/upload";
        } else {
            url = "http://162.243.18.170:9000/v1/resource/create";
        }
        return url;
    }
}
