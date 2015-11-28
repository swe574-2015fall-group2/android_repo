package swe574.boun.edu.androidproject.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Group {
    private final URL mPicture;
    private final String mName;
    private final String mDescription;
    private final String mID;
    private final List<String> mTags;

    public Group(URL mPicture, String mName, String mDescription,String mID, List<String> mTags) {
        this.mPicture = mPicture;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mID = mID;
        this.mTags = mTags;
    }

    public URL getmPicture() {
        return mPicture;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<String> getmTags() {
        return new ArrayList<>(mTags);
    }

    public String getmID() {
        return mID;
    }
}
