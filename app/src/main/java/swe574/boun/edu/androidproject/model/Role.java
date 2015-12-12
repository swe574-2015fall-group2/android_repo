package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class Role implements Parcelable {
    public enum PermissionType{
        CREATE_DISCUSSION, UPDATE_DISCUSSION, COMMENT_TO_DISCUSSION, ADD_REMOVE_TAG_TO_DISCUSSION, CREATE_MEETING, UPDATE_MEETING, INVITE_USER_TO_MEETING, CREATE_MEETING_PROPOSAL, UPDATE_MEETING_PROPOSAL, DELETE_MEETING_PROPOSAL, DELETE_MEETING;

        @Override
        public String toString() {
            String result = null;
            switch (this){
                case CREATE_DISCUSSION:
                    result = "create_discussion";
                    break;
                case UPDATE_DISCUSSION:
                    result = "update_discussion";
                    break;
                case COMMENT_TO_DISCUSSION:
                    result = "comment_to_discussion";
                    break;
                case ADD_REMOVE_TAG_TO_DISCUSSION:
                    result = "add_remove_tag_to_discussion";
                    break;
                case CREATE_MEETING:
                    result = "create_meeting";
                    break;
                case UPDATE_MEETING:
                    result = "update_meeting";
                    break;
                case INVITE_USER_TO_MEETING:
                    result = "invite_user_to_meeting";
                    break;
                case CREATE_MEETING_PROPOSAL:
                    result = "create_meeting_proposal";
                    break;
                case UPDATE_MEETING_PROPOSAL:
                    result = "update_meeting_proposal";
                    break;
                case DELETE_MEETING_PROPOSAL:
                    result = "delete_meeting_proposal";
                    break;
                case DELETE_MEETING:
                    result = "delete_meeting";
                    break;
            }
            return result;
        }

    }
    private final String mID;
    private final String mName;
    private final List<String> mPermissions;

    public Role(String mID, String mName, List<String> mPermissions) {
        this.mID = mID;
        this.mName = mName;
        this.mPermissions = mPermissions;
    }

    protected Role(Parcel in) {
        mID = in.readString();
        mName = in.readString();
        mPermissions = in.createStringArrayList();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public List<String> getmPermissions() {
        List<String> newList = null;
        if (mPermissions != null)
            newList = new ArrayList<>(mPermissions);
        return newList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mName);
        dest.writeList(mPermissions);
    }
}
