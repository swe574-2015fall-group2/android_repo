package swe574.boun.edu.androidproject.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

/**
 * Created by Jongaros on 11/29/2015.
 */
public class GetGroupCalendarTask extends AsyncTask<Void, Void, Void>{
    private ListView mParent;
    private String mAuth;
    private String mGroup;

    public GetGroupCalendarTask(String mGroup, String mAuth, ListView mParent) {
        this.mGroup = mGroup;
        this.mAuth = mAuth;
        this.mParent = mParent;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
