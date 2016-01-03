package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jongaros on 12/19/2015.
 */
public class Tag implements Parcelable {
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
    private String tag;
    private String clazz;
    private String description;

    public Tag() {
    }

    public Tag(String tag, String clazz, String description) {
        this.tag = tag;
        this.clazz = clazz;
        this.description = description;
    }

    protected Tag(Parcel in) {
        tag = in.readString();
        clazz = in.readString();
        description = in.readString();
    }

    public static Tag fromJsonObject(JSONObject jsonObject) throws JSONException {
        Tag newTag = null;

        String _tag = null;
        if (jsonObject.has("tag")) {
            _tag = jsonObject.getString("tag");
        }

        String _class = null;
        if (jsonObject.has("clazz")) {
            _class = jsonObject.getString("clazz");
        }

        String description = null;
        if (jsonObject.has("description")) {
            description = jsonObject.getString("description");
        }

        newTag = new Tag(_tag, _class, description);
        return newTag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String label) {
        this.tag = label;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tag);
        dest.writeString(clazz);
        dest.writeString(description);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("tag", tag);
        if (clazz != null) {
            if (clazz.equals("null") || TextUtils.isEmpty(clazz))
                object.put("clazz", null);
            else {
                object.put("clazz", clazz);
            }
        }
        return object;
    }
}
