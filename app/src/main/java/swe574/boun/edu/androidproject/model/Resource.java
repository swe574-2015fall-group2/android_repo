package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Resource implements Parcelable {
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
    private String id;
    private List<Tag> tagList;
    private String description;
    private EntityType entityType;
    private String link;
    private ResourceType type;
    private Group group;
    private Date createdAt;
    private User creator;

    public Resource(String id, List<Tag> tagList, String mDescription, EntityType entityType, String link, ResourceType type, Group group, Date createdAt, User creator) {
        this.id = id;
        this.tagList = tagList;
        this.description = mDescription;
        this.entityType = entityType;
        this.link = link;
        this.type = type;
        this.group = group;
        this.createdAt = createdAt;
        this.creator = creator;
    }

    protected Resource(Parcel in) {
        id = in.readString();
        tagList = in.createTypedArrayList(Tag.CREATOR);
        description = in.readString();
        entityType = (EntityType) in.readSerializable();
        link = in.readString();
        type = (ResourceType) in.readSerializable();
        group = in.readParcelable(Group.class.getClassLoader());
        createdAt = (Date) in.readSerializable();
        creator = in.readParcelable(User.class.getClassLoader());
    }

    public Resource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeTypedList(tagList);
        dest.writeString(description);
        dest.writeSerializable(entityType);
        dest.writeString(link);
        dest.writeSerializable(type);
        dest.writeParcelable(group, flags);
        dest.writeSerializable(createdAt);
        dest.writeParcelable(creator, flags);
    }
}