package swe574.boun.edu.androidproject.message.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.message.model.Person;

public class PeopleListAdapter extends BaseAdapter{

    Context mContext;
    List<Person> mPersonList;

    public PeopleListAdapter(Context context, List<Person> persons){
        mContext = context;
        mPersonList = persons;
    }

    @Override
    public int getCount() {
        return mPersonList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPersonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup rowView = (ViewGroup) inflater.inflate(R.layout.messagelistitem, null, true);

        ImageView mImageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView mNameView = (TextView) rowView.findViewById(R.id.name);
        TextView mDescriptionView = (TextView) rowView.findViewById(R.id.description);

        Person person = mPersonList.get(position);

        if(mNameView != null){
            mNameView.setText(person.getmFullName());
        }

        if(mDescriptionView != null){
            mDescriptionView.setText(person.getmDescription());
        }

        if(mImageView != null){
            if(person.getmImage() != null){
                mImageView.setImageBitmap(person.getmImage());
            }
            else{
                mImageView.setImageResource(R.drawable.ic_no_person);
            }
        }

        return rowView;
    }
}
