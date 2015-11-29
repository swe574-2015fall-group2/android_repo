package swe574.boun.edu.androidproject.beans;

import java.io.Serializable;
import java.net.URL;

public final class Resource implements Serializable {
    private final String mTitle;
    private final URL mFile;
    private final String mSize;
    private final String mFormat;
    private final boolean mChecked;

    public Resource(String mTitle, URL mFile, String mSize, String mFormat, boolean mChecked) {
        this.mTitle = mTitle;
        this.mFile = mFile;
        this.mSize = mSize;
        this.mFormat = mFormat;
        this.mChecked = mChecked;
    }

    public String getmTitle() {
        return mTitle;
    }

    public URL getmFile() {
        return mFile;
    }

    public String getmSize() {
        return mSize;
    }

    public String getmFormat() {
        return mFormat;
    }

    public boolean ismChecked() {
        return mChecked;
    }
}