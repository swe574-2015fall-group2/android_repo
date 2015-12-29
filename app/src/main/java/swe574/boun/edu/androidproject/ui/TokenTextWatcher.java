package swe574.boun.edu.androidproject.ui;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Tag;

/**
 * Created by Jongaros on 12/29/2015.
 */
public abstract class TokenTextWatcher implements TextWatcher {
    private List<TagData> mTags;
    private int mTokenCount;
    private String[] mTagsArray;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mTagsArray = s.toString().split(",");
        mTokenCount = (mTagsArray.length) / 3;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTagsArray = s.toString().split(",");
        String tag = mTagsArray[mTagsArray.length - 1];
        if (mTokenCount > (mTagsArray.length) / 3) {
            mTags.remove(mTags.size() - 1);
        }
        if (tag.length() < 2) {
            return;
        }

        onTextChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void addTag(TagData tagData){
        if(mTags == null){
            mTags = new ArrayList<>();
        }
        mTags.add(tagData);
    }

    public abstract void onTextChanged();
}
