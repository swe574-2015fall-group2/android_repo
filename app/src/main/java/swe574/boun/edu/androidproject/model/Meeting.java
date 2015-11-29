package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class Meeting implements Parcelable {
    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
    private final String mID;
    private final Date mDate;
    private final List<String> mAgenda;
    private final List<String> mToDo;
    private final long mEstimate;
    private final long mActual;
    private final String mLocation;
    private final String mDescription;
    private final String mStatus;
    private final String mType;
    private final List<User> mInvited;
    private final List<User> mAttended;

    public Meeting(String mID, Date mDate, List<String> mAgenda, List<String> mToDo, long mEstimate, long mActual, String mLocation,
                   String mDescription, String mStatus, String mType, List<User> mInvited, List<User> mAttended) {
        this.mID = mID;
        this.mDate = mDate;
        this.mAgenda = mAgenda;
        this.mToDo = mToDo;
        this.mEstimate = mEstimate;
        this.mActual = mActual;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mStatus = mStatus;
        this.mType = mType;
        this.mInvited = mInvited;
        this.mAttended = mAttended;
    }

    protected Meeting(Parcel in) {
        mID = in.readString();
        mDate = (Date) in.readSerializable();
        mAgenda = in.createStringArrayList();
        mToDo = in.createStringArrayList();
        mEstimate = in.readLong();
        mActual = in.readLong();
        mLocation = in.readString();
        mDescription = in.readString();
        mStatus = in.readString();
        mType = in.readString();
        mInvited = in.createTypedArrayList(User.CREATOR);
        mAttended = in.createTypedArrayList(User.CREATOR);
    }

    public String getmID() {
        return mID;
    }

    public Date getmDate() {
        return mDate;
    }

    public List<String> getmAgenda() {
        return mAgenda;
    }

    public List<String> getmToDo() {
        return mToDo;
    }

    public long getmEstimate() {
        return mEstimate;
    }

    public long getmActual() {
        return mActual;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmType() {
        return mType;
    }

    public List<User> getmInvited() {
        return mInvited;
    }

    public List<User> getmAttended() {
        return mAttended;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeSerializable(mDate);
        dest.writeList(mAgenda);
        dest.writeList(mToDo);
        dest.writeLong(mEstimate);
        dest.writeLong(mActual);
        dest.writeString(mLocation);
        dest.writeString(mDescription);
        dest.writeString(mStatus);
        dest.writeString(mType);
        dest.writeList(mInvited);
        dest.writeList(mAttended);
    }
}
