package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Jongaros on 12/27/2015.
 */
public class Note implements Parcelable {
    private String id;
    private String title;
    private String text;
    private Date createdAt;
    private List<Tag> tagList;

    public Note() {
    }

    public Note(String id, String title, String text, Date createdAt, List<Tag> tagList) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.tagList = tagList;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        text = in.readString();
        createdAt = (Date) in.readSerializable();
        tagList = in.createTypedArrayList(Tag.CREATOR);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeSerializable(createdAt);
        dest.writeTypedList(tagList);
    }
}
