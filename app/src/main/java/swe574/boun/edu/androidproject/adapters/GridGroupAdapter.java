package swe574.boun.edu.androidproject.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import swe574.boun.edu.androidproject.GroupActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;

public class GridGroupAdapter extends BaseAdapter{
    private Context mContext;
    private final ArrayList<Group> mGroups;

    public GridGroupAdapter(Context mContext, ArrayList<Group> mGroups) {
        this.mContext = mContext;
        this.mGroups = mGroups;
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

        String name = g.getmName();
        if(mName != null){
            mName.setText(name);
        }

        String description = g.getmDescription();
        if(mDescription != null){
            mDescription.setText(description);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GroupActivity.class);
                intent.putExtra("id", g.getmID());
                mContext.startActivity(intent);
            }
        });

        view.setBackground(mContext.getResources().getDrawable(R.drawable.style_groupitem));
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 400));

        return view;
    }
}
