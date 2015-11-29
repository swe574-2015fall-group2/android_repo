package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Group implements Parcelable{
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

    protected Group(Parcel in) {
        mPicture = (URL) in.readSerializable();
        mName = in.readString();
        mDescription = in.readString();
        mID = in.readString();
        mTags = in.createStringArrayList();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public URL getmPicture() {
        URL mutable = null;
        try {
            if(mPicture != null)
            mutable = new URL(mPicture.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mutable;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<String> getmTags() {
        ArrayList<String> mutable = new ArrayList<>(mTags);
        return new ArrayList<>(mutable);
    }

    public String getmID() {
        return mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mPicture);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mID);
        dest.writeList(mTags);
    }
}
