package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class Role implements Parcelable {
    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
    private final String mID;
    private final String mName;
    private final List<String> mPermissions;

    public Role(String mID, String mName, List<String> mPermissions) {
        this.mID = mID;
        this.mName = mName;
        this.mPermissions = mPermissions;
    }

    protected Role(Parcel in) {
        mID = in.readString();
        mName = in.readString();
        mPermissions = in.createStringArrayList();
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public List<String> getmPermissions() {
        return mPermissions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mName);
        dest.writeStringList(mPermissions);
    }
}
