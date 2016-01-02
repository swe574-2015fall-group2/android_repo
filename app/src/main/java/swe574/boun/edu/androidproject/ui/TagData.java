package swe574.boun.edu.androidproject.ui;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import swe574.boun.edu.androidproject.model.Tag;

public class TagData implements Serializable {
    @SerializedName("label")
    private String mLabel;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("clazz")
    private String mClass;

    public TagData(String mLabel, String mDescription, String mClass) {
        this.mLabel = mLabel;
        this.mDescription = mDescription;
        this.mClass = mClass;
    }

    public static TagData fromTag(Tag tag) {
        return new TagData(tag.getTag(), tag.getDescription(), tag.getClazz());
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public Tag toTag() {
        return new Tag(mLabel, mClass, mDescription);
    }
}
