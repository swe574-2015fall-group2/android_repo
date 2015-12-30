package swe574.boun.edu.androidproject.requests;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

import swe574.boun.edu.androidproject.model.Resource;
import swe574.boun.edu.androidproject.model.ResourceType;
import swe574.boun.edu.androidproject.network.JSONBuilder;
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
        if(type == ResourceType.INTERNAL){
            url = "http://162.243.18.170:9000/v1/resource/upload";
        }
        else{
            url = "http://162.243.18.170:9000/v1/resource/create";
        }
        return url;
    }
}
