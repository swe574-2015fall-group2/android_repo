package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public final class Resource implements Parcelable {
    public static final Creator<Resource> CREATOR = new Creator<Resource>() {
        @Override
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        @Override
        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };
    private final String mID;
    private final List<Tag> mTags;
    private final String mDescription;
    private final String mEntityType;
    private final String mLink;
    private final String mType;
    private final Group mGroup;
    private final Date mDate;
    private final User mCreator;

    public Resource(String mID, List<Tag> mTags, String mDescription, String mEntityType, String mLink, String mType, Group mGroup, Date mDate, User mCreator) {
        this.mID = mID;
        this.mTags = mTags;
        this.mDescription = mDescription;
        this.mEntityType = mEntityType;
        this.mLink = mLink;
        this.mType = mType;
        this.mGroup = mGroup;
        this.mDate = mDate;
        this.mCreator = mCreator;
    }

    protected Resource(Parcel in) {
        mID = in.readString();
        mTags = in.createTypedArrayList(Tag.CREATOR);
        mDescription = in.readString();
        mEntityType = in.readString();
        mLink = in.readString();
        mType = in.readString();
        mGroup = in.readParcelable(Group.class.getClassLoader());
        mDate = (Date) in.readSerializable();
        mCreator = in.readParcelable(User.class.getClassLoader());
    }

    public String getmID() {
        return mID;
    }

    public List<Tag> getmTags() {
        return mTags;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmEntityType() {
        return mEntityType;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmType() {
        return mType;
    }

    public Group getmGroup() {
        return mGroup;
    }

    public Date getmDate() {
        return mDate;
    }

    public User getmCreator() {
        return mCreator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeTypedList(mTags);
        dest.writeString(mDescription);
        dest.writeString(mEntityType);
        dest.writeString(mLink);
        dest.writeString(mType);
        dest.writeParcelable(mGroup, flags);
        dest.writeSerializable(mDate);
        dest.writeParcelable(mCreator, flags);
    }
}