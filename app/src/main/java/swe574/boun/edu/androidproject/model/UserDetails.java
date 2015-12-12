package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class UserDetails implements Parcelable {
    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
    private final Date mBirthDate;
    private final String mProfession;
    private final String mUniversity;
    private final String mProgramme;
    private final String mInterests;
    private final String mLinkedin;
    private final String mAcademia;

    public UserDetails(Date mBirthDate, String mProfession, String mUniversity, String mProgramme, String mInterests, String mLinkedin, String mAcademia) {
        this.mBirthDate = mBirthDate;
        this.mProfession = mProfession;
        this.mUniversity = mUniversity;
        this.mProgramme = mProgramme;
        this.mInterests = mInterests;
        this.mLinkedin = mLinkedin;
        this.mAcademia = mAcademia;
    }

    protected UserDetails(Parcel in) {
        mBirthDate = (Date) in.readSerializable();
        mProfession = in.readString();
        mUniversity = in.readString();
        mProgramme = in.readString();
        mInterests = in.readString();
        mLinkedin = in.readString();
        mAcademia = in.readString();
    }

    public Date getmBirthDate() {
        return mBirthDate;
    }

    public String getmProfession() {
        return mProfession;
    }

    public String getmUniversity() {
        return mUniversity;
    }

    public String getmProgramme() {
        return mProgramme;
    }

    public String getmInterests() {
        return mInterests;
    }

    public String getmLinkedin() {
        return mLinkedin;
    }

    public String getmAcademia() {
        return mAcademia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mBirthDate);
        dest.writeString(mProfession);
        dest.writeString(mUniversity);
        dest.writeString(mProgramme);
        dest.writeString(mInterests);
        dest.writeString(mLinkedin);
        dest.writeString(mAcademia);
    }
}
