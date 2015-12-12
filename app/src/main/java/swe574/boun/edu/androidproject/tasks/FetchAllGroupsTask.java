package swe574.boun.edu.androidproject.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.MalformedJsonException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import swe574.boun.edu.androidproject.NewGroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.adapters.ListGroupAdapter;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.User;

/**
 * < Parametre Tipi, Progress Tipi, Return Tipi
 */
public class FetchAllGroupsTask extends AsyncTask<Void, Void, ArrayList<Group>> {
    private ViewGroup mView;
    private User mUser;
    private View mGroupForm;
    private ListView mAllGroup;
    private View mProgress;
    private Boolean mResult;

    public FetchAllGroupsTask(ViewGroup mView, User mUser) {
        this.mView = mView;
        this.mUser = mUser;
    }

    /**
     * Runs on main thread. Fetch UI elements here.
     */
    @Override
    protected void onPreExecute() {
        mAllGroup = (ListView) mView.findViewById(R.id.gridViewAllGroups);
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
            URL postUrl = new URL("http://162.243.215.160:9000/v1/group/listAll");
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
                ArrayList<Group> groups = new ArrayList<>();
                if (object.has("groupList")) {
                    JSONArray array = object.getJSONObject("result").getJSONArray("groupList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        groups.add(new Group(null, o.getString("name"), o.getString("description"), o.getString("id"), null, o.getBoolean("joined")));
                    }
                }
                mResult = true;
                return groups;
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
    protected void onPostExecute(ArrayList<Group> result) {
        mGroupForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);

        if (mResult) {
            ListGroupAdapter adapter = new ListGroupAdapter(mView.getContext(), result, mUser);
            mAllGroup.setAdapter(adapter);
        }
        if (mAllGroup.getAdapter() == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mView.getContext(), R.layout.item_nogroups, new String[]{""});
            mAllGroup.setAdapter(adapter);
            mAllGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(mView.getContext(), NewGroupActivity.class);
                    mView.getContext().startActivity(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    protected void onCancelled() {
        mGroupForm.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }
}
