package swe574.boun.edu.androidproject.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;

public class ListGroupAdapter extends BaseAdapter{
    private Context mContext;
    private final ArrayList<Group> mGroups;

    public ListGroupAdapter(ArrayList<Group> mGroups) {
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

        Group g = mGroups.get(position);
        TextView mName = (TextView) view.findViewById(R.id.textViewGroupName);
        TextView mDescription = (TextView) view.findViewById(R.id.textViewDescription);
        TextView mId = (TextView) view.findViewById(R.id.secretid);

        String name = g.getmName();
        if(mName != null){
            mName.setText(name);
        }

        String description = g.getmDescription();
        if(mDescription != null){
            mDescription.setText(description);
        }

        String _ID = g.getmID();
        if(mId != null){
            mId.setText(_ID);
        }

        return view;
    }
}
