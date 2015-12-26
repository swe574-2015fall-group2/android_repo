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
        if (o.has("result")) {
            result = o.getJSONObject("result");
        }
        if (o.has("meeting")) {
            result = o.getJSONObject("meeting");
        }
        if (result.has("meeting")) {
            result = result.getJSONObject("meeting");
        }

        String id = null;
        if (result.has("id")) {
            Object object = result.get("id");
            if (object instanceof String) {
                if ((id = (String) object).equals("null"))
                    id = null;
            }
        }

        String creator = null;
        if (result.has("creatorId")) {
            Object object = result.get("creatorId");
            if (object instanceof String) {
                if ((creator = (String) object).equals("null"))
                    creator = null;
            }
        }

        String group = null;
        if (result.has("groupId")) {
            Object object = result.get("groupId");
            if (object instanceof String) {
                if ((group = (String) object).equals("null"))
                    group = null;
            }
        }

        String name = null;
        if (result.has("name")) {
            Object object = result.get("name");
            if (object instanceof String) {
                if ((name = (String) object).equals("null"))
                    name = null;
            }
        }

        Date date = null;
        if (result.has("datetime")) {
            Object object = result.get("datetime");
            if (object instanceof Long) {
                long time = (long) object;
                if (time != 0) {
                    date = new Date(time);
                }
            }
        }

        String timezone = null;
        if (result.has("timezone")) {
            Object object = result.get("timezone");
            if (object instanceof String) {
                if ((timezone = (String) object).equals("null"))
                    timezone = null;
            }
        }

        List<String> agenda = null;
        if (result.has("agendaSet")) {
            Object object = result.get("agendaSet");
            if (object instanceof JSONArray) {
                JSONArray agendaset = (JSONArray) object;
                if (agendaset != null) {
                    agenda = new ArrayList<>();
                    for (int i = 0; i < agendaset.length(); i++) {
                        agenda.add(agendaset.get(i).toString());
                    }
                }
            }
        }

        List<String> todo = null;
        if (result.has("todoSet")) {
            Object object = result.get("todoSet");
            if (object instanceof JSONArray) {
                JSONArray todoset = (JSONArray) object;
                if (todoset != null) {
                    todo = new ArrayList<>();
                    for (int i = 0; i < todoset.length(); i++) {
                        todo.add(todoset.get(i).toString());
                    }
                }
            }
        }

        String starthour = null;
        if (result.has("startHour")) {
            Object object = result.get("startHour");
            if (object instanceof String) {
                if ((starthour = (String) object).equals("null"))
                    starthour = null;
            }
        }

        String endhour = null;
        if (result.has("endHour")) {
            Object object = result.get("endHour");
            if (object instanceof String) {
                if ((endhour = (String) object).equals("null"))
                    endhour = null;
            }
        }

        long actual = 0;
        if (result.has("actualDuration")) {
            Object object = result.get("endHour");
            if (object instanceof Long) {
                actual = (long) object;
            }
        }

        String location = null;
        if (result.has("location")) {
            Object object = result.get("location");
            if (object instanceof String) {
                if ((location = (String) object).equals("null"))
                    location = null;
            }
        }

        String desc = null;
        if (result.has("description")) {
            Object object = result.get("description");
            if (object instanceof String) {
                if ((desc = (String) object).equals("null"))
                    desc = null;
            }
        }

        String status = null;
        if (result.has("status")) {
            Object object = result.get("status");
            if (object instanceof String) {
                if ((status = (String) object).equals("null"))
                    status = null;
            }
        }

        String type = null;
        if (result.has("type")) {
            Object object = result.get("type");
            if (object instanceof String) {
                if ((type = (String) object).equals("null"))
                    type = null;
            }
        }

        boolean pin = false;
        if (result.has("isPinned")) {
            Object object = result.get("isPinned");
            if (object instanceof Boolean) {
                pin = (boolean) object;
            }
        }

        ContactDetails details = null;
        if (result.has("contactDetails")) {
            Object object = result.get("contactDetails");
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                if (jsonObject.keys().hasNext()) {

                    String cname = null;
                    if (jsonObject.has("name")) {
                        object = jsonObject.get("name");
                        if (object instanceof String) {
                            if ((cname = (String) object).equals("null"))
                                cname = null;
                        }
                    }

                    String csurname = null;
                    if (jsonObject.has("surname")) {
                        object = jsonObject.get("surname");
                        if (object instanceof String) {
                            if ((csurname = (String) object).equals("null"))
                                csurname = null;
                        }
                    }

                    String cmail = null;
                    if (jsonObject.has("email")) {
                        object = jsonObject.get("email");
                        if (object instanceof String) {
                            if ((cmail = (String) object).equals("null"))
                                cmail = null;
                        }
                    }

                    String cphone = null;
                    if (jsonObject.has("phoneNumber")) {
                        object = jsonObject.get("phoneNumber");
                        if (object instanceof String) {
                            if ((cphone = (String) object).equals("null"))
                                cphone = null;
                        }
                    }

                    details = new ContactDetails(cname, csurname, cmail, cphone);
                }
            }
        }

        List<String> invited = null;
        if (result.has("invitedUserSet")) {
            Object object = result.get("invitedUserSet");
            if ((object instanceof JSONArray)) {
                JSONArray inviset = (JSONArray) object;
                if (inviset != null) {
                    invited = new ArrayList<>();
                    for (int i = 0; i < inviset.length(); i++) {
                        invited.add(inviset.get(i).toString());
                    }
                }
            }
        }

        List<String> attended = null;
        if (result.has("attandedUserSet")) {
            Object object = result.get("attandedUserSet");
            if ((object instanceof JSONArray)) {
                JSONArray attset = result.getJSONArray("attandedUserSet");
                if (attset != null) {
                    attended = new ArrayList<>();
                    for (int i = 0; i < attset.length(); i++) {
                        attended.add(attset.get(i).toString());
                    }
                }
            }
        }

        List<String> rejected = null;
        if (result.has("rejectedUserSet")) {
            Object object = result.get("rejectedUserSet");
            if ((object instanceof JSONArray)) {
                JSONArray attset = result.getJSONArray("rejectedUserSet");
                if (attset != null) {
                    rejected = new ArrayList<>();
                    for (int i = 0; i < attset.length(); i++) {
                        rejected.add(attset.get(i).toString());
                    }
                }
            }
        }

        List<String> tentitive = null;
        if (result.has("tentativeUserSet")) {
            Object object = result.get("tentativeUserSet");
            if ((object instanceof JSONArray)) {
                JSONArray attset = result.getJSONArray("tentativeUserSet");
                if (attset != null) {
                    tentitive = new ArrayList<>();
                    for (int i = 0; i < attset.length(); i++) {
                        tentitive.add(attset.get(i).toString());
                    }
                }
            }
        }

        List<Tag> tags = null;
        if (result.has("tagList")) {
            Object object = result.get("tagList");
            if ((object instanceof JSONArray)) {
                tags = new ArrayList<>();
                JSONArray array = result.getJSONArray("tagList");

                for (int i = 0; i < array.length(); i++) {
                    Tag tag = Tag.fromJsonObject(array.getJSONObject(i));
                    tags.add(tag);
                }
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
