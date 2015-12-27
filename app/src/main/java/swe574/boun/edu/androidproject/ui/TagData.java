package swe574.boun.edu.androidproject.ui;

import java.io.Serializable;

import swe574.boun.edu.androidproject.model.Tag;

public class TagData implements Serializable {
    private String mLabel;
    private String mDescription;
    private String mClass;

    public TagData(String mLabel, String mDescription, String mClass) {
        this.mLabel = mLabel;
        this.mDescription = mDescription;
        this.mClass = mClass;
    }

    public static TagData fromTag(Tag tag) {
        return new TagData(tag.getLabel(), tag.getDescription(), tag.getClazz());
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
        return new Tag(mLabel, mDescription, mClass);
    }
}
