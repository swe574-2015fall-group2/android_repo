package swe574.boun.edu.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import swe574.boun.edu.androidproject.fragments.GroupsNavigationFragment;
import swe574.boun.edu.androidproject.fragments.HomeNavigationFragment;
import swe574.boun.edu.androidproject.fragments.MessageNavigationFragment;
import swe574.boun.edu.androidproject.fragments.ProfileNavigationFragment;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.network.JSONRequest;
import swe574.boun.edu.androidproject.network.RequestQueueBuilder;
import swe574.boun.edu.androidproject.ui.TagData;
import swe574.boun.edu.androidproject.ui.TagsArrayAdapter;
import swe574.boun.edu.androidproject.ui.TagsCompletionView;
import swe574.boun.edu.androidproject.ui.TagsErrorListener;
import swe574.boun.edu.androidproject.ui.TagsResponseListener;
import swe574.boun.edu.androidproject.ui.TokenTextWatcher;

public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Codes
    public static final int NEW_GROUP = 1;
    int code = 0;
    // Activity datas
    private User mUser;
    private List<TagData> mTagDataList;
    // UI Elements
    private NavigationView mNavigationView;
    private TagsCompletionView mSearchEditTextView;
    private TagsArrayAdapter mTagsArrayAdapter;
    private Button mSearchButton;
    //Network Variables
    private RequestQueue mRequestQueue;
    private JSONRequest mTagsRequest;
    private JSONRequest mSearchRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeNavigationFragment()).commit();
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mUser = extras.getParcelable("user");

        mSearchEditTextView = (TagsCompletionView) findViewById(R.id.search_bar);
        mSearchEditTextView.setMaxLines(1);
        mSearchEditTextView.setMovementMethod(new ScrollingMovementMethod());
        mSearchEditTextView.setVerticalScrollBarEnabled(true);

        mTagDataList = new ArrayList<>();
        mTagsArrayAdapter = new TagsArrayAdapter(this, R.layout.tag_layout, mTagDataList);
        mRequestQueue = RequestQueueBuilder.preapareSerialQueue(this);
        mRequestQueue.start();

        TokenTextWatcher tokenTextWatcher = new TokenTextWatcher() {
            @Override
            public void onTextChanged(String tag) {
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("authToken", App.mAuth);
                    jsonObject.accumulate("queryString", tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTagsRequest = new JSONRequest("http://162.243.18.170:9000/v1/semantic/queryLabel", new TagsResponseListener(mTagsArrayAdapter), new TagsErrorListener(jsonObject), jsonObject);
                mRequestQueue.add(mTagsRequest);
            }
        };
        mSearchEditTextView.configurate(mTagsArrayAdapter, new TokenCompleteTextView.TokenListener() {
            @Override
            public void onTokenAdded(Object token) {

            }

            @Override
            public void onTokenRemoved(Object token) {

            }
        }, tokenTextWatcher);

        mSearchButton = (Button) findViewById(R.id.ButtonSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSearchRequest = new JSONRequest()
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestQueue.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestQueue.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        mRequestQueue.stop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager manager = getSupportFragmentManager();
            super.onBackPressed();
            HomeFragment currentFragment = (HomeFragment) manager.findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof HomeNavigationFragment) {
                mNavigationView.getMenu().getItem(0).setChecked(true);
            } else if (currentFragment instanceof GroupsNavigationFragment) {
                mNavigationView.getMenu().getItem(2).setChecked(true);
            } else if (currentFragment instanceof ProfileNavigationFragment) {
                mNavigationView.getMenu().getItem(1).setChecked(true);
            } else if (currentFragment instanceof MessageNavigationFragment) {
                mNavigationView.getMenu().getItem(3).setChecked(true);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences preferences = getSharedPreferences("logged", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        HomeFragment fragment = null;
        Class type = null;
        switch (id) {
            case R.id.nav_home:
                type = HomeNavigationFragment.class;
                break;
            case R.id.nav_groups:
                type = GroupsNavigationFragment.class;
                break;
            case R.id.nav_profile:
                type = ProfileNavigationFragment.class;
                break;
            case R.id.nav_messages:
                type = MessageNavigationFragment.class;
                break;
        }
        fragment = HomeFragment.newInstance(mUser, type);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("fragment" + code++).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
