package swe574.boun.edu.androidproject.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jongaros on 12/12/2015.
 */
public final class Image implements Parcelable {
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
    private final String mType;
    private final Bitmap mImage;

    public Image(String mType, Bitmap mImage) {
        this.mType = mType;
        this.mImage = mImage;
    }

    protected Image(Parcel in) {
        mType = in.readString();
        mImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public String getmType() {
        return mType;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mType);
        dest.writeParcelable(mImage, flags);
    }
}
