package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jongaros on 12/12/2015.
 */
public final class UserRole implements Parcelable {
    private final String mGroupID;
    private final Role mRole;

    public UserRole(String mGroupID, Role mRole) {
        this.mGroupID = mGroupID;
        this.mRole = mRole;
    }

    protected UserRole(Parcel in) {
        mGroupID = in.readString();
        mRole = in.readParcelable(Role.class.getClassLoader());
    }

    public static final Creator<UserRole> CREATOR = new Creator<UserRole>() {
        @Override
        public UserRole createFromParcel(Parcel in) {
            return new UserRole(in);
        }

        @Override
        public UserRole[] newArray(int size) {
            return new UserRole[size];
        }
    };

    public String getmGroupID() {
        return mGroupID;
    }

    public Role getmRole() {
        return mRole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGroupID);
        dest.writeParcelable(mRole, flags);
    }
}
