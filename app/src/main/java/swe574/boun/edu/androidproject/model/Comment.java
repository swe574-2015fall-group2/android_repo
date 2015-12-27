package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Jongaros on 12/27/2015.
 */
public class Comment implements Parcelable {
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    private String comment;
    private Date creationTime;
    private String creatorId;

    public Comment() {

    }

    public Comment(String comment, Date creationTime, String creatorId) {
        this.comment = comment;
        this.creationTime = creationTime;
        this.creatorId = creatorId;
    }

    protected Comment(Parcel in) {
        comment = in.readString();
        creationTime = (Date) in.readSerializable();
        creatorId = in.readString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeSerializable(creationTime);
        dest.writeString(creatorId);
    }
}
