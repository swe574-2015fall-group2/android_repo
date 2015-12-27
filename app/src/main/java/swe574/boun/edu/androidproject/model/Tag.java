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
    private String label;
    private String clazz;
    private String description;

    public Tag() {
    }

    public Tag(String label, String clazz, String description) {
        this.label = label;
        this.clazz = clazz;
        this.description = description;
    }

    protected Tag(Parcel in) {
        label = in.readString();
        clazz = in.readString();
        description = in.readString();
    }

    public static Tag fromJsonObject(JSONObject jsonObject) throws JSONException {
        Tag tag = null;

        String label = null;
        if (jsonObject.has("label")) {
            label = jsonObject.getString("label");
        } else if (jsonObject.has("tag")) {
            label = jsonObject.getString("tag");
        }

        String _class = null;
        if (jsonObject.has("clazz")) {
            _class = jsonObject.getString("clazz");
        }

        String description = null;
        if (jsonObject.has("description")) {
            description = jsonObject.getString("description");
        }

        tag = new Tag(label, _class, description);
        return tag;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        dest.writeString(label);
        dest.writeString(clazz);
        dest.writeString(description);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("tag", label);
        if (clazz.equals("null") || clazz == null || TextUtils.isEmpty(clazz))
            object.put("clazz", null);
        else {
            object.put("clazz", clazz);
        }
        return object;
    }
}
