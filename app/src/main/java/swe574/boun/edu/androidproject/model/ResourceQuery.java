package swe574.boun.edu.androidproject.model;

import java.util.List;

/**
 * Created by Jongaros on 1/2/2016.
 */
public class ResourceQuery {
    private String status;
    private List<Resource> result;

    public ResourceQuery(String status, List<Resource> result) {
        this.status = status;
        this.result = result;
    }

    public ResourceQuery() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Resource> getResult() {
        return result;
    }

    public void setResult(List<Resource> result) {
        this.result = result;
    }
}
