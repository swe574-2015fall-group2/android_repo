package swe574.boun.edu.androidproject.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import swe574.boun.edu.androidproject.network.OnVolleyError;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class GenericErrorListener implements Response.ErrorListener {
    @NonNull
    private JSONObject mRequest;
    @Nullable
    private OnVolleyError mListener;

    public GenericErrorListener(JSONObject mRequest, OnVolleyError mListener) {
        this.mRequest = mRequest;
        this.mListener = mListener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("REQUEST", mRequest.toString());
        Log.e("RESPONSE", new String(error.networkResponse.data));
        if (mListener != null) {
            mListener.onVolleyError(error);
        }
    }
}
