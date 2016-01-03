package swe574.boun.edu.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jongaros on 12/30/2015.
 */
public class SearchResult implements Parcelable{
    private List<SearchDetail> resultList;
    private List<String> clazzList;

    public SearchResult() {
    }

    public SearchResult(List<SearchDetail> resultList, List<String> clazzList) {
        this.resultList = resultList;
        this.clazzList = clazzList;
    }

    protected SearchResult(Parcel in) {
        resultList = in.createTypedArrayList(SearchDetail.CREATOR);
        clazzList = in.createStringArrayList();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public List<SearchDetail> getResultList() {
        return resultList;
    }

    public void setResultList(List<SearchDetail> resultList) {
        this.resultList = resultList;
    }

    public List<String> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<String> clazzList) {
        this.clazzList = clazzList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(resultList);
        dest.writeStringList(clazzList);
    }

    public static class SearchDetail implements Parcelable{
        private EntityType type;
        private String id;
        private String description;
        private Tag tag;
        private int rank;

        public SearchDetail(EntityType type, String id, String description, Tag tag, int rank) {
            this.type = type;
            this.id = id;
            this.description = description;
            this.tag = tag;
            this.rank = rank;
        }

        public SearchDetail() {
        }

        protected SearchDetail(Parcel in) {
            type = (EntityType) in.readSerializable();
            id = in.readString();
            description = in.readString();
            tag = in.readParcelable(Tag.class.getClassLoader());
            rank = in.readInt();
        }

        public static final Creator<SearchDetail> CREATOR = new Creator<SearchDetail>() {
            @Override
            public SearchDetail createFromParcel(Parcel in) {
                return new SearchDetail(in);
            }

            @Override
            public SearchDetail[] newArray(int size) {
                return new SearchDetail[size];
            }
        };

        public EntityType getType() {
            return type;
        }

        public void setType(EntityType type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Tag getTag() {
            return tag;
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(type);
            dest.writeString(id);
            dest.writeString(description);
            dest.writeParcelable(tag, flags);
            dest.writeInt(rank);
        }
    }
}
