package swe574.boun.edu.androidproject.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokenautocomplete.FilteredArrayAdapter;

import java.util.List;

import swe574.boun.edu.androidproject.R;

/**
 * Created by Jongaros on 12/29/2015.
 */
public class TagsArrayAdapter extends FilteredArrayAdapter<TagData> {
    public TagsArrayAdapter(Context context, int resource, TagData[] objects) {
        super(context, resource, objects);
    }

    public TagsArrayAdapter(Context context, int resource, int textViewResourceId, TagData[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public TagsArrayAdapter(Context context, int resource, List<TagData> objects) {
        super(context, resource, objects);
    }

    public TagsArrayAdapter(Context context, int resource, int textViewResourceId, List<TagData> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = l.inflate(R.layout.tag_layout, parent, false);
        }

        final TagData tagData = getItem(position);
        ((TextView) convertView.findViewById(R.id.label)).setText(tagData.getmLabel());
        if (tagData.getmDescription() == null || tagData.getmDescription().equals("null")) {
            ((TextView) convertView.findViewById(R.id.description)).setText("");
        } else {
            ((TextView) convertView.findViewById(R.id.description)).setText(tagData.getmDescription());
        }

        return convertView;
    }

    @Override
    protected boolean keepObject(TagData obj, String mask) {
        return true;
    }
}
