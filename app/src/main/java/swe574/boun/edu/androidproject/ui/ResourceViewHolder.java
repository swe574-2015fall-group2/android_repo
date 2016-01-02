package swe574.boun.edu.androidproject.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import swe574.boun.edu.androidproject.R;

/**
 * Created by Jongaros on 1/2/2016.
 */
public class ResourceViewHolder extends RecyclerView.ViewHolder {
    protected TextView resourceDescriptionTextView;

    public ResourceViewHolder(View itemView) {
        super(itemView);
        resourceDescriptionTextView = (TextView) itemView.findViewById(R.id.resourceDescriptionTextView);
    }
}
