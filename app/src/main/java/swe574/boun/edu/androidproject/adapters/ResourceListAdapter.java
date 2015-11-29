package swe574.boun.edu.androidproject.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.beans.Resource;

public class ResourceListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Resource> mResources;

    public ResourceListAdapter(Context mContext, List<Resource> mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public Object getItem(int position) {
        return mResources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.resourcelistitem, null, true);

        TextView mSize = (TextView) viewGroup.findViewById(R.id.textViewSize);
        TextView mFormat = (TextView) viewGroup.findViewById(R.id.textViewFormat);

        Resource resource = mResources.get(position);

        if (mSize != null) {
            mSize.setText(resource.getmSize());
        }

        if (mFormat != null) {
            mFormat.setText(resource.getmFormat());
        }

        return viewGroup;
    }
}
