package swe574.boun.edu.androidproject.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class Group implements Parcelable {
    public static final int MY_GROUPS = 1;
    public static final int ALL_GROUPS = 2;
    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
    private final Image mImage;
    private final String mName;
    private final String mDescription;
    private final String mID;
    private final List<Tag> mTags;
    private final List<User> mUsers;
    private final boolean mJoined;

    public Group(Image mImage, String mName, String mDescription, String mID, List<Tag> mTags, boolean mJoined, List<User> mUsers) {
        this.mImage = mImage;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mID = mID;
        this.mTags = mTags;
        this.mJoined = mJoined;
        this.mUsers = mUsers;
    }

    protected Group(Parcel in) {
        mImage = in.readParcelable(Image.class.getClassLoader());
        mName = in.readString();
        mDescription = in.readString();
        mID = in.readString();
        mTags = in.createTypedArrayList(Tag.CREATOR);
        mUsers = in.createTypedArrayList(User.CREATOR);
        mJoined = in.readByte() != 0;
    }

    public static Group fromJsonString(JSONObject json) throws JSONException {
        Group group = null;
        JSONObject result = json;
        if (json.has("result")) {
            result = json.getJSONObject("result");
        }

        String id = null;
        if (result.has("id")) {
            id = result.getString("id");
        }

        String name = null;
        if (result.has("name")) {
            name = result.getString("name");
        }

        String description = null;
        if (result.has("description")) {
            description = result.getString("description");
        }

        boolean joined = false;
        if (result.has("joined")) {
            joined = result.getBoolean("joined");
        }

        Image image = null;
        if (result.has("image")) {
            JSONObject jsonObject = result.getJSONObject("image");

            String type = null;
            if (jsonObject.has("type")) {
                type = jsonObject.getString("type");
            }

            Bitmap bitmap = null;
            if (jsonObject.has("base64Image")) {
                String base64Image = jsonObject.getString("base64Image");
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap original = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap resized = Bitmap.createScaledBitmap(original, 200, 200, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length);
            }

            image = new Image(type, bitmap);
        }

        List<Tag> tags = null;
        if (result.has("tagList")) {
            tags = new ArrayList<>();
            Object o = result.get("tagList");
            if (o != null) {
                if (o instanceof JSONArray) {
                    JSONArray array = (JSONArray) o;

                    for (int i = 0; i < array.length(); i++) {
                        Tag tag = Tag.fromJsonObject(array.getJSONObject(i));
                        tags.add(tag);
                    }
                }
            }
        }

        List<User> users = null;
        if (result.has("userList")) {
            users = new ArrayList<>();
            JSONArray array = result.getJSONArray("userList");

            for (int i = 0; i < array.length(); i++) {
                User user = User.createFromJSON(null, array.getJSONObject(i));
                users.add(user);
            }
        }
        group = new Group(image, name, description, id, tags, joined, users);
        return group;
    }

    public Image getmImage() {
        return mImage;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<Tag> getmTags() {
        return mTags;
    }

    public String getmID() {
        return mID;
    }

    public boolean ismJoined() {
        return mJoined;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mImage, flags);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mID);
        dest.writeTypedList(mTags);
        dest.writeTypedList(mUsers);
        dest.writeByte((byte) (mJoined ? 1 : 0));
    }
}
