package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jongaros on 12/19/2015.
 */
public final class Tag implements Parcelable{
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
    private final String mLabel;
    private final String mClass;
    private final String mDescription;

    public Tag(String mLabel, String mClass, String mDescription) {
        this.mLabel = mLabel;
        this.mClass = mClass;
        this.mDescription = mDescription;
    }

    protected Tag(Parcel in) {
        mLabel = in.readString();
        mClass = in.readString();
        mDescription = in.readString();
    }

    public static Tag fromJsonObject(JSONObject jsonObject) throws JSONException {
        Tag tag = null;

        String label = null;
        if(jsonObject.has("label")){
            label = jsonObject.getString("label");
        }
        else if(jsonObject.has("tag")){
            label = jsonObject.getString("tag");
        }

        String _class = null;
        if(jsonObject.has("clazz")){
            _class = jsonObject.getString("clazz");
        }

        String description = null;
        if(jsonObject.has("description")){
            description = jsonObject.getString("description");
        }

        tag = new Tag(label, _class, description);
        return tag;
    }

    public String getmLabel() {
        return mLabel;
    }

    public String getmClass() {
        return mClass;
    }

    public String getmDescription() {
        return mDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLabel);
        dest.writeString(mClass);
        dest.writeString(mDescription);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("tag", mLabel);
        if(mClass.equals("null") || mClass == null || TextUtils.isEmpty(mClass))
        object.put("clazz", null);
        else{
            object.put("clazz", mClass);
        }
        return object;
    }
}
