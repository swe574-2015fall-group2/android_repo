package swe574.boun.edu.androidproject.model;

/**
 * Created by Jongaros on 12/30/2015.
 */
public enum EntityType {
    DISCUSSION, GROUP, MEETING, NOTE, RESOURCE;

    @Override
    public String toString() {
        String string;
        switch (this) {
            case DISCUSSION:
                string = "Discussion";
                break;
            case GROUP:
                string = "Group";
                break;
            case MEETING:
                string = "Meeting";
                break;
            case NOTE:
                string = "Note";
                break;
            case RESOURCE:
                string = "Resource";
                break;
            default:
                string = "";
                break;
        }
        return string;
    }
}
