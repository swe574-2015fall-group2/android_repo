package swe574.boun.edu.androidproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.SearchResult;

/**
 * Created by Jongaros on 1/3/2016.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {
    private SearchResult mResult;

    public SearchResultsAdapter(SearchResult mResult) {
        this.mResult = mResult;
    }

    @Override
    public SearchResultsAdapter.SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchResultsViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(final SearchResultsAdapter.SearchResultsViewHolder holder, int position) {
        final SearchResult.SearchDetail result = mResult.getResultList().get(position);
        holder.mEntityTypeTextView.setText(result.getType().toString());
        holder.mIdTextView.setText(result.getId());
        holder.mDescriptionTextView.setText(result.getDescription());
        // No SQL injection here ^^
        holder.mTagTextView.setText("#" + result.getTag().getTag() + " (" + result.getTag().getClazz() + ")" );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (result.getType()){
                    case GROUP:
                        break;
                    case DISCUSSION:
                        break;
                    case NOTE:
                        break;
                    case MEETING:
                        break;
                    case RESOURCE:
                        Toast.makeText(holder.itemView.getContext(), "This feature is currently in development", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.getResultList().size();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView mEntityTypeTextView;
        public TextView mIdTextView;
        public TextView mDescriptionTextView;
        public TextView mTagTextView;

        public SearchResultsViewHolder(View itemView) {
            super(itemView);
            mEntityTypeTextView = (TextView) itemView.findViewById(R.id.entityTypeTextView);
            mIdTextView = (TextView) itemView.findViewById(R.id.idTextView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            mTagTextView = (TextView) itemView.findViewById(R.id.tagTextView);
        }
    }
}
