package swe574.boun.edu.androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.List;

import swe574.boun.edu.androidproject.beans.Resource;

public class ResourcesActivity extends AppCompatActivity {
    private ListView mResourceList;
    private List<Resource> mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        mResourceList.setAdapter(null);

    }

}
