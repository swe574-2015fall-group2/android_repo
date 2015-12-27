package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jongaros on 12/27/2015.
 */
public class Discussion implements Parcelable {
    public static final Creator<Discussion> CREATOR = new Creator<Discussion>() {
        @Override
        public Discussion createFromParcel(Parcel in) {
            return new Discussion(in);
        }

        @Override
        public Discussion[] newArray(int size) {
            return new Discussion[size];
        }
    };
    private String id;
    private String name;
    private String description;
    private String creatorId;
    private String groupId;
    private List<Comment> commentList;
    private List<Tag> tagList;

    public Discussion() {
    }

    protected Discussion(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        creatorId = in.readString();
        groupId = in.readString();
        commentList = in.createTypedArrayList(Comment.CREATOR);
        tagList = in.createTypedArrayList(Tag.CREATOR);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(creatorId);
        dest.writeString(groupId);
        dest.writeTypedList(commentList);
        dest.writeTypedList(tagList);
    }
}
