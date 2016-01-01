package swe574.boun.edu.androidproject.ui;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.model.Tag;

/**
 * Created by Jongaros on 12/31/2015.
 */
public class TagsResponseListener implements Response.Listener<String> {
    private TagsArrayAdapter mAdapter;
    private RequestQueue mRequestQueue;

    public TagsResponseListener(TagsArrayAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public void onResponse(String response) {
        List<TagData> tagList = new ArrayList<>();
        Log.v("RESPONSE", response);
        try {
            JSONObject result = new JSONObject(response);
            if (result.has("result")) {
                result = result.getJSONObject("result");
            } else {
                return;
            }
            JSONArray array = result.getJSONArray("dataList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject tagObject = array.getJSONObject(i);
                tagList.add(TagData.fromTag(Tag.fromJsonObject(tagObject)));
            }
            mAdapter.clear();
            mAdapter.addAll(tagList);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
