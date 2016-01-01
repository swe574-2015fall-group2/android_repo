package swe574.boun.edu.androidproject.ui;

import android.text.Editable;
import android.text.TextWatcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jongaros on 12/29/2015.
 */
public abstract class TokenTextWatcher implements TextWatcher {
    private List<TagData> mTags;
    private int mTokenCount;
    private String[] mTagsArray;
    private RequestQueue mRequestQueue;

    public TokenTextWatcher(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(mRequestQueue != null)
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
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

        onTextChanged(tag);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void addTag(TagData tagData) {
        if (mTags == null) {
            mTags = new ArrayList<>();
        }
        mTags.add(tagData);
    }

    public abstract void onTextChanged(String tag);
}
