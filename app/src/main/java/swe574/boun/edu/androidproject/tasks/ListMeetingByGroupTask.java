package swe574.boun.edu.androidproject.tasks;

import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import swe574.boun.edu.androidproject.R;

/**
 * < Parametre Tipi, Progress Tipi, Return Tipi
 */
public class ListMeetingByGroupTask extends AsyncTask<Void, Void, Boolean> {
    private Activity mActivity;
    private String mAuthToken;
    private String mMeetingId;
    private Date mMeetingDateTime;
    private String mMeetingAgendaSet;
    private String mMeetingToDoSet;
    private int mMeetingEstimatedDuration;
    private int mMeetingActualDuration;
    private String mMeetingLocation;
    private String mMeetingDescription;
    private String mMeetingStatus;
    private String mMeetingType;
    private String mMeetingInvitedUserSet;
    private String mMeetingAttendedUserSet;
    private View mMeetingForm;
    private View mProgress;

    public ListMeetingByGroupTask(Activity mActivity, String mAuthToken, ViewGroup mParent) {
        super();
        this.mActivity = mActivity;
        this.mAuthToken = mAuthToken;
        this.mProgress = mParent.findViewById(R.id.meeting_progress);
        this.mMeetingForm = mParent.findViewById(R.id.meeting_form);
    }

    /**
     * Runs on main thread. Fetch UI elements here.
     */
    @Override
    protected void onPreExecute() {
        mMeetingForm = mActivity.findViewById(R.id.listMeetings);
        mMeetingForm.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/meeting/queryByGroup");
            // Open the created connection to server.
            HttpURLConnection httpURLConnection = (HttpURLConnection) postUrl.openConnection();
            // Set up the post parameters
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // Create JSON String
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("authToken", mAuthToken);
            jsonObject.accumulate("Id", mMeetingId);
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
            int response  = httpURLConnection.getResponseCode();
            // Get the Response
            String responseJson = "";
            if(response == HttpURLConnection.HTTP_OK){
                //Response is okay
                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((line=reader.readLine()) != null) {
                    responseJson += line;
                }
            }
            else{
                // Server is down or webserver is changed.
                return false;
            }
            JSONObject object = new JSONObject(responseJson);
            if(object != null){
                // DO SOMETHING WITH RESULTS
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mMeetingForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);

        if(result){

        }
    }

    @Override
    protected void onCancelled() {

        mMeetingForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }
}
