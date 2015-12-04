package swe574.boun.edu.androidproject.tasks;

import android.os.AsyncTask;
import android.util.MalformedJsonException;
import android.view.View;
import android.view.ViewGroup;

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

import swe574.boun.edu.androidproject.R;

public class CreateDiscussionTask extends AsyncTask<Void, Void, Boolean> {
    private final String mAuth;
    private final View mProgressView;
    private final View mFormView;
    private String mGroupName;
    private String mGroupDescription;


    public CreateDiscussionTask(String mAuth, ViewGroup mParent) {
        super();
        this.mAuth = mAuth;
        this.mDiscussionName = ((EditText) mParent.findViewById(R.id.discussionName)).getText().toString();
        this.mDiscussionDescription = ((EditText) mParent.findViewById(R.id.discussionDescription)).getText().toString();
        this.mDiscussionGroupId = ((EditText) mParent.findViewById(R.id.discussionGroupId)).getText().toString();
        this.mProgressView = mParent.findViewById(R.id.discussion_progress);
        this.mFormView = mParent.findViewById(R.id.discussion_form);
    }

    @Override
    protected void onPreExecute() {
        mProgressView.setVisibility(View.VISIBLE);
        mFormView.setVisibility(View.GONE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result = false;
        HttpURLConnection httpURLConnection = null;
        try {
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/discussion/create");
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
            jsonObject.accumulate("groupId", mDiscussionGroupId);
            jsonObject.accumulate("name", mDiscussionName);
            jsonObject.accumulate("description", mDiscussionDescription);
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
                result = true;
                httpURLConnection.disconnect();
                return result;
            } else {
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
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mProgressView.setVisibility(View.GONE);
        mFormView.setVisibility(View.VISIBLE);
        String message;
        if (result) {
            message = "You have successfully created discussion " /*+ mMeetingName*/;
        } else {
            message = "Discussion creation failed, please check your parameters. " /*+ mMeetingName*/;
        }
    }

    @Override
    protected void onCancelled() {
        mProgressView.setVisibility(View.GONE);
        mFormView.setVisibility(View.VISIBLE);
    }

}
