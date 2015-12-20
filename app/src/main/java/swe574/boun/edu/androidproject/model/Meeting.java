package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jongaros on 11/29/2015.
 */
public final class Meeting implements Parcelable {
    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
    private final String mID;
    private final String mCreatorID;
    private final String mGroupID;
    private final String mName;
    private final Date mDate;
    private final String mTimeZone;
    private final List<String> mAgenda;
    private final List<String> mToDo;
    private final String mStartHour;
    private final String mEndHour;
    private final long mActual;
    private final String mLocation;
    private final String mDescription;
    private final String mStatus;
    private final String mType;
    private final boolean isPin;
    private final ContactDetails mDetails;
    private final List<String> mInvited;
    private final List<String> mAttended;
    private final List<String> mRejected;
    private final List<String> mTentative;
    private final List<Tag> mTags;

    public Meeting(String mID, String mCreatorID, String mGroupID, String mName, Date mDate, String mTimeZone, List<String> mAgenda,
                   List<String> mToDo, String mStartHour, String mEndHour, long mActual, String mLocation, String mDescription,
                   String mStatus, String mType, boolean isPin, ContactDetails mDetails, List<String> mInvited, List<String> mAttended,
                   List<String> mRejected, List<String> mTentative, List<Tag> mTags) {
        this.mID = mID;
        this.mCreatorID = mCreatorID;
        this.mGroupID = mGroupID;
        this.mName = mName;
        this.mDate = mDate;
        this.mTimeZone = mTimeZone;
        this.mAgenda = mAgenda;
        this.mToDo = mToDo;
        this.mStartHour = mStartHour;
        this.mEndHour = mEndHour;
        this.mActual = mActual;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mStatus = mStatus;
        this.mType = mType;
        this.isPin = isPin;
        this.mDetails = mDetails;
        this.mInvited = mInvited;
        this.mAttended = mAttended;
        this.mRejected = mRejected;
        this.mTentative = mTentative;
        this.mTags = mTags;
    }

    protected Meeting(Parcel in) {
        mID = in.readString();
        mCreatorID = in.readString();
        mGroupID = in.readString();
        mName = in.readString();
        mDate = (Date) in.readSerializable();
        mTimeZone = in.readString();
        mAgenda = in.createStringArrayList();
        mToDo = in.createStringArrayList();
        mStartHour = in.readString();
        mEndHour = in.readString();
        mActual = in.readLong();
        mLocation = in.readString();
        mDescription = in.readString();
        mStatus = in.readString();
        mType = in.readString();
        isPin = in.readByte() != 0;
        mDetails = in.readParcelable(ContactDetails.class.getClassLoader());
        mInvited = in.createStringArrayList();
        mAttended = in.createStringArrayList();
        mRejected = in.createStringArrayList();
        mTentative = in.createStringArrayList();
        mTags = in.createTypedArrayList(Tag.CREATOR);
    }

    public static Meeting createFromJSON(JSONObject o) throws JSONException {
        Meeting meeting = null;
        JSONObject result = o;
        if(o.has("result")){
            result = o.getJSONObject("result");
        }
        if(o.has("meeting")){
            result = o.getJSONObject("meeting");
        }
        if(result.has("meeting")){
            result = result.getJSONObject("meeting");
        }

        String id = null;
        if(result.has("id")){
            id = result.getString("id");
        }

        String creator = null;
        if(result.has("creatorId")){
            creator = result.getString("creatorId");
        }

        String group = null;
        if(result.has("groupId")){
            group = result.getString("groupId");
        }

        String name = null;
        if(result.has("name")){
            name = result.getString("name");
        }

        Date date = null;
        if (result.has("datetime")) {
            date = (Date) result.get("datetime");
        }

        String timezone = null;
        if(result.has("timezone")){
            timezone = result.getString("timezone");
        }

        List<String> agenda = null;
        if (result.has("agendaSet")) {
            JSONArray agendaset = result.getJSONArray("agendaSet");
            if (agendaset != null) {
                agenda = new ArrayList<>();
                for (int i = 0; i < agendaset.length(); i++) {
                    agenda.add(agendaset.get(i).toString());
                }
            }
        }

        List<String> todo = null;
        if (result.has("todoSet")) {
            JSONArray todoset = result.getJSONArray("todoSet");
            if (todoset != null) {
                todo = new ArrayList<>();
                for (int i = 0; i < todoset.length(); i++) {
                    todo.add(todoset.get(i).toString());
                }
            }
        }

        String starthour = null;
        if (result.has("startHour")){
            starthour = result.getString("startHour");
        }

        String endhour = null;
        if (result.has("endHour")){
            endhour = result.getString("endHour");
        }

        long actual = 0;
        if (result.has("actualDuration")) {
            actual = o.getLong("actualDuration");
        }

        String location = null;
        if (result.has("location")) {
            location = result.getString("location");
        }

        String desc = null;
        if (result.has("description")) {
            desc = result.getString("description");
        }

        String status = null;
        if (result.has("status")) {
            status = result.getString("status");
        }

        String type = null;
        if (result.has("type")) {
            type = result.getString("type");
        }

        boolean pin = false;
        if( result.has("isPinned")){
            pin = result.getBoolean("isPinned");
        }

        ContactDetails details = null;
        if(result.has("contactDetails")){
            JSONObject object = result.getJSONObject("contactDetails");

            String cname = null;
            if(object.has("name")){
                cname = object.getString("name");
            }

            String csurname = null;
            if(object.has("surname")){
                csurname = object.getString("surname");
            }

            String cmail = null;
            if(object.has("email")){
                cmail = object.getString("email");
            }

            String cphone = null;
            if(object.has("phoneNumber")){
                cphone = object.getString("phoneNumber");
            }

            details = new ContactDetails(cname, csurname, cmail, cphone);
        }

        List<String> invited = null;
        if (result.has("invitedUserSet")) {
            JSONArray inviset = result.getJSONArray("invitedUserSet");
            if (inviset != null) {
                invited = new ArrayList<>();
                for (int i = 0; i < inviset.length(); i++) {
                    invited.add(inviset.get(i).toString());
                }
            }
        }

        List<String> attended = null;
        if (result.has("attandedUserSet")) {
            JSONArray attset = result.getJSONArray("attandedUserSet");
            if (attset != null) {
                attended = new ArrayList<>();
                for (int i = 0; i < attset.length(); i++) {
                    attended.add(attset.get(i).toString());
                }
            }
        }

        List<String> rejected = null;
        if (result.has("rejectedUserSet")) {
            JSONArray attset = result.getJSONArray("rejectedUserSet");
            if (attset != null) {
                rejected = new ArrayList<>();
                for (int i = 0; i < attset.length(); i++) {
                    rejected.add(attset.get(i).toString());
                }
            }
        }

        List<String> tentitive = null;
        if (result.has("tentativeUserSet")) {
            JSONArray attset = result.getJSONArray("tentativeUserSet");
            if (attset != null) {
                tentitive = new ArrayList<>();
                for (int i = 0; i < attset.length(); i++) {
                    tentitive.add(attset.get(i).toString());
                }
            }
        }

        List<Tag> tags = null;
        if(result.has("tagList")){
            tags = new ArrayList<>();
            JSONArray array = result.getJSONArray("tagList");

            for(int i = 0 ; i < array.length() ; i++){
                Tag tag = Tag.fromJsonObject(array.getJSONObject(i));
                tags.add(tag);
            }
        }

        meeting = new Meeting(id, creator, group, name, date, timezone, agenda, todo, starthour, endhour, actual, location, desc, status, type, pin, details, invited, attended, rejected, tentitive, tags);
        return meeting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mCreatorID);
        dest.writeString(mGroupID);
        dest.writeString(mName);
        dest.writeSerializable(mDate);
        dest.writeString(mTimeZone);
        dest.writeStringList(mAgenda);
        dest.writeStringList(mToDo);
        dest.writeString(mStartHour);
        dest.writeString(mEndHour);
        dest.writeLong(mActual);
        dest.writeString(mLocation);
        dest.writeString(mDescription);
        dest.writeString(mStatus);
        dest.writeString(mType);
        dest.writeByte((byte) (isPin ? 1 : 0));
        dest.writeParcelable(mDetails, flags);
        dest.writeStringList(mInvited);
        dest.writeStringList(mAttended);
        dest.writeStringList(mRejected);
        dest.writeStringList(mTentative);
        dest.writeTypedList(mTags);
    }

    public String getmID() {
        return mID;
    }

    public String getmCreatorID() {
        return mCreatorID;
    }

    public String getmGroupID() {
        return mGroupID;
    }

    public String getmName() {
        return mName;
    }

    public Date getmDate() {
        return mDate;
    }

    public String getmTimeZone() {
        return mTimeZone;
    }

    public List<String> getmAgenda() {
        return mAgenda;
    }

    public List<String> getmToDo() {
        return mToDo;
    }

    public String getmStartHour() {
        return mStartHour;
    }

    public String getmEndHour() {
        return mEndHour;
    }

    public long getmActual() {
        return mActual;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmType() {
        return mType;
    }

    public boolean isPin() {
        return isPin;
    }

    public ContactDetails getmDetails() {
        return mDetails;
    }

    public List<String> getmInvited() {
        return mInvited;
    }

    public List<String> getmAttended() {
        return mAttended;
    }

    public List<String> getmRejected() {
        return mRejected;
    }

    public List<String> getmTentative() {
        return mTentative;
    }

    public List<Tag> getmTags() {
        return mTags;
    }
}
