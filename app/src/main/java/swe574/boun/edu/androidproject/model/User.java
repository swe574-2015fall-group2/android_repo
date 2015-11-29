package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class User implements Parcelable {
    private final String mID;
    private final String mUsername;
    private final String mName;
    private final String mSurname;
    private final String mPassword;
    private final URL mPicture;
    private final List<Role> mRoles;
    private final UserDetails mDetails;
    private final List<User> mBlockedUsers;
    private final String mStatus;

    public User(String mID, String mUsername, String mName, String mSurname, String mPassword, URL mPicture,
                List<Role> mRoles, UserDetails mDetails, List<User> mBlockedUsers, String mStatus) {
        this.mID = mID;
        this.mUsername = mUsername;
        this.mName = mName;
        this.mSurname = mSurname;
        this.mPassword = mPassword;
        this.mPicture = mPicture;
        this.mRoles = mRoles;
        this.mDetails = mDetails;
        this.mBlockedUsers = mBlockedUsers;
        this.mStatus = mStatus;
    }

    protected User(Parcel in) {
        mID = in.readString();
        mUsername = in.readString();
        mName = in.readString();
        mSurname = in.readString();
        mPassword = in.readString();
        mPicture = (URL) in.readSerializable();
        mRoles = in.createTypedArrayList(Role.CREATOR);
        mDetails = in.readParcelable(UserDetails.class.getClassLoader());
        mBlockedUsers = in.createTypedArrayList(User.CREATOR);
        mStatus = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getmID() {
        return mID;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmName() {
        return mName;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmPassword() {
        return mPassword;
    }

    public URL getmPicture() {
        return mPicture;
    }

    public List<Role> getmRoles() {
        return mRoles;
    }

    public UserDetails getmDetails() {
        return mDetails;
    }

    public List<User> getmBlockedUsers() {
        return mBlockedUsers;
    }

    public String getmStatus() {
        return mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mUsername);
        dest.writeString(mName);
        dest.writeString(mSurname);
        dest.writeString(mPassword);
        dest.writeSerializable(mPicture);
        dest.writeList(mRoles);
        dest.writeParcelable(mDetails, flags);
        dest.writeList(mBlockedUsers);
        dest.writeString(mStatus);
    }
}
