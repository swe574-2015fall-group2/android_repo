package swe574.boun.edu.androidproject.model;

import java.util.List;

public final class Group {
    private final String mName;
    private final String mDescription;
    private final List<String> mTags;

    public Group(String mName, String mDescription, List<String> mTags) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTags = mTags;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<String> getmTags() {
        return mTags;
    }
}
