package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jongaros on 12/12/2015.
 */
public final class Image implements Parcelable{
    private final String mType;
    private final String mImage;

    public Image(String mType, String mImage) {
        this.mType = mType;
        this.mImage = mImage;
    }

    protected Image(Parcel in) {
        mType = in.readString();
        mImage = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getmType() {
        return mType;
    }

    public String getmImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mType);
        dest.writeString(mImage);
    }
}
