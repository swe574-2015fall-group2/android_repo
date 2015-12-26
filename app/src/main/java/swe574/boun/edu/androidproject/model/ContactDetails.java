package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jongaros on 12/20/2015.
 */
public final class ContactDetails implements Parcelable {
    public static final Creator<ContactDetails> CREATOR = new Creator<ContactDetails>() {
        @Override
        public ContactDetails createFromParcel(Parcel in) {
            return new ContactDetails(in);
        }

        @Override
        public ContactDetails[] newArray(int size) {
            return new ContactDetails[size];
        }
    };
    private final String mName;
    private final String mSurname;
    private final String mMail;
    private final String mPhone;

    public ContactDetails(String mName, String mSurname, String mMail, String mPhone) {
        this.mName = mName;
        this.mSurname = mSurname;
        this.mMail = mMail;
        this.mPhone = mPhone;
    }

    protected ContactDetails(Parcel in) {
        mName = in.readString();
        mSurname = in.readString();
        mMail = in.readString();
        mPhone = in.readString();
    }

    public String getmName() {
        return mName;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmMail() {
        return mMail;
    }

    public String getmPhone() {
        return mPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mSurname);
        dest.writeString(mMail);
        dest.writeString(mPhone);
    }
}
