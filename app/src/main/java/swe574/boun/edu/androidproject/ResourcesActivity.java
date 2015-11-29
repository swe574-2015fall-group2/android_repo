package swe574.boun.edu.androidproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.adapters.ResourceListAdapter;
import swe574.boun.edu.androidproject.beans.Resource;

public class ResourcesActivity extends AppCompatActivity {
    private View mResourceForm;
    private View mProgressView;
    private ListView mResourceList;
    private List<Resource> mResources;
    private UserResourceTask mResourceTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        mResourceList = (ListView) findViewById(R.id.listViewResources);
        mProgressView = findViewById(R.id.get_progress);
        mResourceForm = findViewById(R.id.resource_form);

        mResourceTask = new UserResourceTask(mResourceList);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String token = preferences.getString("token", "");

        mResourceTask.execute(token);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mResourceForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mResourceForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mResourceForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mResourceForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserResourceTask extends AsyncTask<String, Void, List<Resource>> {
        private ListView mResourceList;
        private List<Resource> mResources;

        public UserResourceTask(ListView mResourceList) {
            this.mResourceList = mResourceList;
            mResources = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected List<Resource> doInBackground(String... params) {
            String userToken = params[0];
            //TODO Implement Web Service
            return null;
        }

        @Override
        protected void onPostExecute(List<Resource> resources) {
            mResourceTask = null;
            showProgress(false);

            ResourceListAdapter adapter = new ResourceListAdapter(ResourcesActivity.this, resources);
            mResourceList.setAdapter(adapter);
            mResourceList.invalidateViews();

        }
    }

}
