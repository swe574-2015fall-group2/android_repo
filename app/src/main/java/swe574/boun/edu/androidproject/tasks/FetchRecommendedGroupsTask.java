package swe574.boun.edu.androidproject.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.adapters.ListGroupAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.OnTaskCompleted;
import swe574.boun.edu.androidproject.model.User;

/**
 * Created by Jongaros on 12/12/2015.
 */
public class FetchRecommendedGroupsTask extends AsyncTask<Void, Void, ArrayList<Group>> {
    private OnTaskCompleted mListener;
    private ViewGroup mView;
    private User mUser;
    private View mGroupForm;
    private ListView mMyGroup;
    private View mProgress;
    private Boolean mResult;

    public FetchRecommendedGroupsTask(ViewGroup mView, User mUser, OnTaskCompleted mListener) {
        this.mView = mView;
        this.mUser = mUser;
        this.mListener = mListener;
    }

    /**
     * Runs on main thread. Fetch UI elements here.
     */
    @Override
    protected void onPreExecute() {
        mMyGroup = (ListView) mView.findViewById(R.id.gridViewRecommendedGroups);
        mMyGroup.setAdapter(null);
        mGroupForm = mView.findViewById(R.id.group_form);
        mProgress = mView.findViewById(R.id.group_progress);
        mGroupForm.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Group> doInBackground(Void... params) {
        mResult = false;
        HttpURLConnection httpURLConnection = null;
        try {
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/group/listRecommended");
            // Open the created connection to server.
            httpURLConnection = (HttpURLConnection) postUrl.openConnection();
            // Set up the post parameters
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // Create JSON String
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("authToken", App.mAuth);
            String json = jsonObject.toString();
            // Create request output stream.
            OutputStream outputStream = httpURLConnection.getOutputStream();
            // Create a writer to write on the output stream.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            // Send the post request
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();
            httpURLConnection.connect();
            // Get response code
            int response = httpURLConnection.getResponseCode();
            // Get the Response
            String responseJson = "";
            if (response == HttpURLConnection.HTTP_OK) {
                //Response is okay
                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseJson += line;
                }
            } else {
                // Server is down or webserver is changed.
                throw new IllegalStateException("Response code is not valid");
            }
            httpURLConnection.disconnect();
            JSONObject object = new JSONObject(responseJson);
            if (object.getString("status").equals("success")) {
                ArrayList<Group> results = new ArrayList<>();
                mResult = true;
                object = object.getJSONObject("result");

                if (object.has("groupList")) {
                    JSONArray groupList = object.getJSONArray("groupList");
                    for (int i = 0; i < groupList.length(); i++) {
                        Group group = Group.fromJsonString(groupList.getJSONObject(i));
                        results.add(group);
                    }
                }
                return results;
            } else {
                // User is not registered to any groups
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Group> result) {
        mGroupForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);

        if (mResult) {
            mMyGroup.setOnTouchListener(null);
            ListGroupAdapter adapter = new ListGroupAdapter(mView.getContext(), result, mUser);
            mMyGroup.setAdapter(adapter);
        }
        mListener.onTaskCompleted(null);
    }

    @Override
    protected void onCancelled() {
        mGroupForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }
}
