package swe574.boun.edu.androidproject.tasks;

import android.os.AsyncTask;
import android.util.MalformedJsonException;
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
import java.util.Date;

import swe574.boun.edu.androidproject.adapters.MeetingListAdapter;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Meeting;

/**
 * Created by Jongaros on 11/29/2015.
 */
public class GetGroupCalendarTask extends AsyncTask<Void, Void, ArrayList<Meeting>> {
    private ListView mParent;
    private String mAuth;
    private Group mGroup;
    private boolean mResult;

    public GetGroupCalendarTask(Group mGroup, String mAuth, ListView mParent) {
        this.mGroup = mGroup;
        this.mAuth = mAuth;
        this.mParent = mParent;
    }

    @Override
    protected ArrayList<Meeting> doInBackground(Void... params) {
        mResult = false;
        HttpURLConnection httpURLConnection = null;
        try {
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/meeting/queryByGroup");
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
            jsonObject.accumulate("authToken", mAuth);
            jsonObject.accumulate("id", mGroup.getmID());
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
            JSONObject object = new JSONObject(responseJson);
            if (object.getString("status").equals("success")) {
                ArrayList<Meeting> meetings = new ArrayList<>();
                JSONArray array = object.getJSONObject("result").getJSONArray("meetingList");
                int limit = array.length() > 4 ? 4 : array.length();
                for (int i = 0; i < limit; i++) {
                    JSONObject o = array.getJSONObject(i);
                    meetings.add(Meeting.createFromJSON(o));
                }
                mResult = true;
                httpURLConnection.disconnect();
                return meetings;
            } else {
                if(!object.getString("consumerMessage").equals("Meeting not found"))
                throw new MalformedJsonException("Returned JSON String isn't fit the format.");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Meeting> result) {
        if(mResult){
            // TODO MeetingListAdapter adapter = new MeetingListAdapter();
            // mParent.setAdapter(adapter);
        }
    }
}
