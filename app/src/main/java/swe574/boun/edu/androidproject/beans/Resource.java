package swe574.boun.edu.androidproject.beans;

import java.io.Serializable;
import java.net.URL;

public final class Resource implements Serializable{
    private final String mTitle;
    private final URL mFile;
    private final boolean mChecked;

    public Resource(String mTitle, URL mFile, boolean mChecked) {
        this.mTitle = mTitle;
        this.mFile = mFile;
        this.mChecked = mChecked;
    }

    public String getmTitle() {
        return mTitle;
    }

    public URL getmFile() {
        return mFile;
    }

    public boolean ismChecked() {
        return mChecked;
    }
}