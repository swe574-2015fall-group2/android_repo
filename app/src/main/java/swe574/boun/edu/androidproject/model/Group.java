package swe574.boun.edu.androidproject.model;

import java.util.ArrayList;
import java.util.List;

public final class Group {
    private final String mName;
    private final String mDescription;
    private final String mID;
    private final List<String> mTags;

    public Group(String mName, String mDescription,String mID, List<String> mTags) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mID = mID;
        this.mTags = mTags;
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
