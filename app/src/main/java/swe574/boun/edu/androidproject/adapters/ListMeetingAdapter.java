package swe574.boun.edu.androidproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Meeting;
import swe574.boun.edu.androidproject.model.User;

/**
 * Created by O_KOCYIGIT on 30.11.2015.
 */
public class ListMeetingAdapter extends BaseAdapter {
    private User mUser;
    private List<Meeting> mMeetings;
    private Context mContext;

    public ListMeetingAdapter(User mUser, Context mContext, List<Meeting> mMeetings) {
        this.mUser = mUser;
        this.mContext = mContext;
        this.mMeetings = mMeetings;
    }

    @Override
    public int getCount() {
        return mMeetings.size();
    }

    @Override
    public Object getItem(int position) {
        return mMeetings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.listitem_meeting, null, false);

        final Meeting meeting = mMeetings.get(position);
        if (meeting.getmDate() != null) {
            Calendar meetingDate = Calendar.getInstance();
            meetingDate.setTime(meeting.getmDate());

            TextView mDay = (TextView) viewGroup.findViewById(R.id.meeting_day);
            mDay.setText((String.valueOf(meetingDate.get(Calendar.DATE))));

            TextView mMonth = (TextView) viewGroup.findViewById(R.id.meeting_month);
            SimpleDateFormat monthName = new SimpleDateFormat("MMM");
            mMonth.setText(monthName.format(meetingDate.getTime()));

            TextView mYear = (TextView) viewGroup.findViewById(R.id.meeting_year);
            mYear.setText(String.valueOf(meetingDate.get(Calendar.YEAR)));
        }

        TextView mDesc = (TextView) viewGroup.findViewById(R.id.meeting_description);
        if (meeting.getmDescription() != null) {
            mDesc.setText(meeting.getmDescription());
        }

        TextView mLocation = (TextView) viewGroup.findViewById(R.id.meeting_location);
        if (meeting.getmLocation() != null) {
            mLocation.setText(meeting.getmLocation());
        }

        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("user", mUser);
                i.putExtra("meeting", meeting);
                mContext.startActivity(i);
            }
        });

        viewGroup.setBackground(mContext.getResources().getDrawable(R.drawable.style_groupitem));

        return viewGroup;
    }
}
