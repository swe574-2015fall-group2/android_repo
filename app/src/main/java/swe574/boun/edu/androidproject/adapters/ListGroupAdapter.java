package swe574.boun.edu.androidproject.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import swe574.boun.edu.androidproject.GroupTabbedActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;

public class ListGroupAdapter extends BaseAdapter{
    private Context mContext;
    private final ArrayList<Group> mGroups;
    private final String mAuth;

    public ListGroupAdapter(Context mContext, ArrayList<Group> mGroups, String mAuth) {
        this.mContext = mContext;
        this.mGroups = mGroups;
        this.mAuth = mAuth;
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.listitem_group, null , false);

        final Group g = mGroups.get(position);
        TextView mName = (TextView) view.findViewById(R.id.textViewGroupName);
        TextView mDescription = (TextView) view.findViewById(R.id.textViewDescription);
        ImageView mImage = (ImageView) view.findViewById(R.id.group_image);

        String name = g.getmName();
        if(mName != null){
            mName.setText(name);
        }

        String description = g.getmDescription();
        if(mDescription != null){
            mDescription.setText(description);
        }

        URL url = g.getmPicture();
        if(url != null){
            try {
                InputStream stream = (InputStream) url.getContent();
                Drawable drawable = Drawable.createFromStream(stream, "not 9patch");
                mImage.setBackground(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GroupTabbedActivity.class);
                intent.putExtra("user", mAuth);
                intent.putExtra("name", g.getmID());
                mContext.startActivity(intent);
            }
        });

        view.setBackground(mContext.getResources().getDrawable(R.drawable.style_groupitem));
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 200));

        return view;
    }
}
