package swe574.boun.edu.androidproject.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Resource;
import swe574.boun.edu.androidproject.ui.ResourceViewHolder;

public class ResourceListAdapter extends RecyclerView.Adapter<ResourceViewHolder> {
    private ListViewAdapterListener mAdapter;
    private List<Resource> mResources;

    public ResourceListAdapter(List<Resource> mResources, ListViewAdapterListener mAdapter) {
        this.mResources = mResources;
        this.mAdapter = mAdapter;
    }

    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.resourcelistitem, parent, false);
        mAdapter.onViewCreated(viewGroup);
        return new ResourceViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(ResourceViewHolder holder, int position) {
        Resource resource = mResources.get(position);
        TextView resourceDescriptionTextView = (TextView) holder.itemView.findViewById(R.id.resourceDescriptionTextView);
        resourceDescriptionTextView.setText(resource.getDescription());

    }

    @Override
    public int getItemCount() {
        return mResources.size();
    }
}
