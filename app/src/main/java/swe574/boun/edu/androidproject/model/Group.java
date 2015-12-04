package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Group implements Parcelable {
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
    private final URL mPicture;
    private final String mName;
    private final String mDescription;
    private final String mID;
    private final List<String> mTags;
    private final boolean mJoined;

    public Group(URL mPicture, String mName, String mDescription, String mID, List<String> mTags, boolean mJoined) {
        this.mPicture = mPicture;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mID = mID;
        this.mTags = mTags;
        this.mJoined = mJoined;
    }

    protected Group(Parcel in) {
        mPicture = (URL) in.readSerializable();
        mName = in.readString();
        mDescription = in.readString();
        mID = in.readString();
        mTags = in.createStringArrayList();
        mJoined = in.readByte() != 0;
    }

    public URL getmPicture() {
        URL mutable = null;
        try {
            if (mPicture != null)
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

    public boolean ismJoined() {
        return mJoined;
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
        dest.writeByte((byte) (mJoined ? 1 : 0));
    }
}
