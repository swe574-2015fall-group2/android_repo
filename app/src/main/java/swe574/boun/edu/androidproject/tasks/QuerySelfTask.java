package swe574.boun.edu.androidproject.tasks;

import android.content.Intent;
import android.os.AsyncTask;
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

import swe574.boun.edu.androidproject.HomeDrawerActivity;
import swe574.boun.edu.androidproject.LoginActivity;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.User;

/**
 * Created by Jongaros on 12/12/2015.
 */
public class QuerySelfTask extends AsyncTask<String, Void, User> {
    private LoginActivity mActivity;

    public QuerySelfTask(LoginActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected User doInBackground(String... params) {
        String id = params[0];
        try {
            HttpURLConnection httpURLConnection = null;
            // Create a new UrlConnection
            URL postUrl = new URL("http://162.243.215.160:9000/v1/user/get");
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
            jsonObject.accumulate("id", id);
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
                return User.createFromJSON(id,object);
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
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        Intent intent = new Intent(mActivity, HomeDrawerActivity.class);
        intent.putExtra("user", user);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
