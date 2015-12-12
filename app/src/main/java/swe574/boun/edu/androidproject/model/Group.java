package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class Group implements Parcelable {
    public static final int MY_GROUPS = 1;
    public static final int ALL_GROUPS = 2;
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
    private final Image mImage;
    private final String mName;
    private final String mDescription;
    private final String mID;
    private final List<String> mTags;
    private final boolean mJoined;

    public Group(Image mImage, String mName, String mDescription, String mID, List<String> mTags, boolean mJoined) {
        this.mImage = mImage;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mID = mID;
        this.mTags = mTags;
        this.mJoined = mJoined;
    }

    protected Group(Parcel in) {
        mImage = in.readParcelable(Image.class.getClassLoader());
        mName = in.readString();
        mDescription = in.readString();
        mID = in.readString();
        mTags = in.createStringArrayList();
        mJoined = in.readByte() != 0;
    }

    public Image getmImage() {
        return mImage;
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
        dest.writeParcelable(mImage, flags);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mID);
        dest.writeStringList(mTags);
        dest.writeByte((byte) (mJoined ? 1 : 0));
    }
}
