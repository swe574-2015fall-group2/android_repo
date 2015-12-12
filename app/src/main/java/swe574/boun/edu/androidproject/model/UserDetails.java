package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
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
    private final URL mLinkedin;
    private final URL mAcademia;

    public UserDetails(Date mBirthDate, String mProfession, String mUniversity, String mProgramme,
                       String mInterests, URL mLinkedin, URL mAcademia) {
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
        mLinkedin = (URL) in.readSerializable();
        mAcademia = (URL) in.readSerializable();
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

    public URL getmLinkedin() {
        URL url = null;
        if (mLinkedin != null) {
            try {
                url = new URL(mLinkedin.getPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public URL getmAcademia() {
        URL url = null;
        if (mAcademia != null) {
            try {
                url = new URL(mAcademia.getPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
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
        dest.writeSerializable(mLinkedin);
        dest.writeSerializable(mAcademia);
    }
}
