package swe574.boun.edu.androidproject.tasks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.MalformedJsonException;

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

import swe574.boun.edu.androidproject.message.App;

/**
 * Created by Jongaros on 12/13/2015.
 */
public class UploadProfilePictureTask extends AsyncTask<Void, Void, Boolean> {
    private final String TYPE = "JPG";
    private OnTaskCompleted mListener;
    private byte[] mPicture;
    private String mUserID;

    public UploadProfilePictureTask(OnTaskCompleted mListener, byte[] mPicture, String mUserID) {
        this.mListener = mListener;
        this.mPicture = mPicture;
        this.mUserID = mUserID;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpURLConnection httpURLConnection = null;
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/user/uploadImage");
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
            jsonObject.accumulate("entityId", mUserID);
            jsonObject.accumulate("fileType", TYPE);
            String base64Image = null;
            if (mPicture != null) {
                if (mPicture.length > 0) {
                    base64Image = Base64.encodeToString(mPicture, Base64.DEFAULT);
                }
            }
            jsonObject.accumulate("base64Image", base64Image);
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
                return true;
            } else {
                throw new MalformedJsonException("Returned JSON String isn't fit the format.");
            }
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
    protected void onPostExecute(Boolean aBoolean) {
        Bundle extras = new Bundle();
        extras.putBoolean("success", aBoolean);
        mListener.onTaskCompleted(extras);
    }
}
