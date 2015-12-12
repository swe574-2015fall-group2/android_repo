package swe574.boun.edu.androidproject.tasks;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.MalformedJsonException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import swe574.boun.edu.androidproject.message.App;

public final class CreateGroupTask extends AsyncTask<Void, Void, Boolean> {
    private final Activity mActivity;
    private final Context mContext;
    private final String mName;
    private final String mDescription;
    private final View mProgressView;
    private final View mFormView;
    private String mErrorMessage;

    public CreateGroupTask(Activity mActivity, Context mContext, ViewGroup mParent) {
        super();
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mName = ((EditText) mParent.findViewById(R.id.groupName)).getText().toString();
        this.mDescription = ((EditText) mParent.findViewById(R.id.groupDesc)).getText().toString();
        this.mProgressView = mParent.findViewById(R.id.group_progress);
        this.mFormView = mParent.findViewById(R.id.group_form);
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
            URL postUrl = new URL("http://162.243.215.160:9000/v1/group/create");
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
            jsonObject.accumulate("name", mName);
            jsonObject.accumulate("description", mDescription);
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
                result = true;
                return result;
            }
            else {
                mErrorMessage = object.getString("consumerMessage");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mProgressView.setVisibility(View.GONE);
        mFormView.setVisibility(View.VISIBLE);
        String message;
        if (result) {
            message = "You have successfully created group " + mName;
        } else {
            message = mErrorMessage;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        if (result){
            mActivity.setResult(Activity.RESULT_OK);
            mActivity.finish();
        }
    }

    @Override
    protected void onCancelled() {
        mProgressView.setVisibility(View.GONE);
        mFormView.setVisibility(View.VISIBLE);
    }
}
