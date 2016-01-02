package swe574.boun.edu.androidproject.model;

import java.util.List;

/**
 * Created by Jongaros on 12/30/2015.
 */
public class SearchResult {
    private List<SearchDetail> resultList;
    private List<String> clazzList;

    public SearchResult() {
    }

    public SearchResult(List<SearchDetail> resultList, List<String> clazzList) {
        this.resultList = resultList;
        this.clazzList = clazzList;
    }

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

    private static class SearchDetail {
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
    }
}
