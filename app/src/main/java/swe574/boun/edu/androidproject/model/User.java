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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private final String mID;
    private final String mUsername;
    private final String mName;
    private final String mSurname;
    private final Image mImage;
    private final List<UserRole> mRoles;
    private final UserDetails mDetails;
    private final String mStatus;

    public User(String mID, String mUsername, String mName, String mSurname, Image mImage, List<UserRole> mRoles, UserDetails mDetails, String mStatus) {
        this.mID = mID;
        this.mUsername = mUsername;
        this.mName = mName;
        this.mSurname = mSurname;
        this.mImage = mImage;
        this.mRoles = mRoles;
        this.mDetails = mDetails;
        this.mStatus = mStatus;
    }

    protected User(Parcel in) {
        mID = in.readString();
        mUsername = in.readString();
        mName = in.readString();
        mSurname = in.readString();
        mImage = in.readParcelable(Image.class.getClassLoader());
        mRoles = in.createTypedArrayList(UserRole.CREATOR);
        mDetails = in.readParcelable(UserDetails.class.getClassLoader());
        mStatus = in.readString();
    }

    public static User createFromJSON(String mID, JSONObject object) throws JSONException {
        JSONObject result = object;
        if (object.has("result")) {
            result = (JSONObject) object.get("result");
        }

        String username = null;
        if (result.has("username")) {
            username = result.getString("username");
        }

        String firstname = null;
        if (result.has("firstname")) {
            firstname = result.getString("firstname");
        }

        String lastname = null;
        if (result.has("lastname")) {
            lastname = result.getString("lastname");
        }

        String status = null;
        if (result.has("status")) {
            status = result.getString("status");
        }

        UserDetails details = null;
        if (result.has("userDetail")) {
            JSONObject userDetail = result.getJSONObject("userDetail");

            Date birthDate = null;
            if (userDetail.has("birthDate")) {
                birthDate = (Date) userDetail.get("birthDate");
            }

            String profession = null;
            if (userDetail.has("profession")) {
                profession = userDetail.getString("profession");
            }

            String university = null;
            if (userDetail.has("university")) {
                university = userDetail.getString("university");
            }

            String programme = null;
            if (userDetail.has("programme")) {
                programme = userDetail.getString("programme");
            }

            String interestedAreas = null;
            if (userDetail.has("interestedAreas")) {
                interestedAreas = userDetail.getString("interestedAreas");
            }

            String linkedinProfile = null;
            if (userDetail.has("linkedinProfile")) {
                linkedinProfile = userDetail.getString("linkedinProfile");
            }

            String academiaProfile = null;
            if (userDetail.has("academiaProfile")) {
                academiaProfile = userDetail.getString("academiaProfile");
            }
            details = new UserDetails(birthDate, profession, university, programme, interestedAreas, linkedinProfile, academiaProfile);
        }

        Image image = null;
        if (result.has("image")) {
            JSONObject imageObject = result.getJSONObject("image");

            String type = null;
            if (imageObject.has("type")) {
                type = imageObject.getString("type");
            }

            String base64Image;
            Bitmap bitmap = null;
            if (imageObject.has("base64Image")) {
                base64Image = imageObject.getString("base64Image");
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap original = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap resized = Bitmap.createScaledBitmap(original, 200, 200, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length);
            }
            image = new Image(type, bitmap);
        }

        List<UserRole> roles = null;
        if (result.has("roles")) {
            roles = new ArrayList<>();
            JSONArray roleArray = result.getJSONArray("roles");
            for (int i = 0; i < roleArray.length(); i++) {
                JSONObject userRole = roleArray.getJSONObject(i);

                String groupID = null;
                if (userRole.has("groupId")) {
                    groupID = userRole.getString("groupId");
                }

                Role role = null;
                if (userRole.has("groupRoles")) {
                    JSONObject roleObject = userRole.getJSONArray("groupRoles").getJSONObject(0);

                    String roleID = null;
                    if (roleObject.has("id")) {
                        roleID = roleObject.getString("id");
                    }

                    String roleName = null;
                    if (roleObject.has("name")) {
                        roleName = roleObject.getString("name");
                    }

                    List<String> rolePermissions = null;
                    if (roleObject.has("permissions")) {
                        rolePermissions = new ArrayList<>();
                        JSONArray permissionArray = roleObject.getJSONArray("permissions");
                        for (int j = 0; j < permissionArray.length(); j++) {
                            rolePermissions.add(permissionArray.getString(j));
                        }
                    }
                    role = new Role(roleID, roleName, rolePermissions);
                }
                roles.add(new UserRole(groupID, role));
            }
        }

        return new User(mID, username, firstname, lastname, image, roles, details, status);
    }

    public String getmID() {
        return mID;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmName() {
        return mName;
    }

    public String getmSurname() {
        return mSurname;
    }

    public Image getmImage() {
        return mImage;
    }

    public UserDetails getmDetails() {
        return mDetails;
    }

    public String getmStatus() {
        return mStatus;
    }

    public List<UserRole> getmRoles() {
        return mRoles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mUsername);
        dest.writeString(mName);
        dest.writeString(mSurname);
        dest.writeParcelable(mImage, flags);
        dest.writeTypedList(mRoles);
        dest.writeParcelable(mDetails, flags);
        dest.writeString(mStatus);
    }
}
