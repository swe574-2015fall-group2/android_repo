package swe574.boun.edu.androidproject.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import swe574.boun.edu.androidproject.GroupTabbedActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Group;
import swe574.boun.edu.androidproject.model.Image;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.tasks.ApplyGroupTask;
import swe574.boun.edu.androidproject.tasks.OnTaskCompleted;

public class ListGroupAdapter extends BaseAdapter {
    private final ArrayList<Group> mGroups;
    private final User mUser;
    private Context mContext;

    public ListGroupAdapter(Context mContext, ArrayList<Group> mGroups, User mUser) {
        this.mContext = mContext;
        this.mGroups = mGroups;
        this.mUser = mUser;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.listitem_group, null, false);

        final Group g = mGroups.get(position);
        TextView mName = (TextView) view.findViewById(R.id.textViewGroupName);
        TextView mDescription = (TextView) view.findViewById(R.id.textViewDescription);
        mDescription.setMovementMethod(new ScrollingMovementMethod());
        ImageView mImage = (ImageView) view.findViewById(R.id.group_image);

        String name = g.getmName();
        if (mName != null) {
            mName.setText(name);
        }

        String description = g.getmDescription();
        if (mDescription != null) {
            mDescription.setText(description);
        }
        Image image = g.getmImage();
        if (image != null) {
            Bitmap picture = g.getmImage().getmImage();
            if (picture != null) {
                mImage.setImageBitmap(picture);
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (g.ismJoined()) {
                    Intent intent = new Intent(mContext, GroupTabbedActivity.class);
                    intent.putExtra("user", mUser);
                    intent.putExtra("group", g);
                    mContext.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Join " + g.getmName());
                    builder.setMessage("Do you want to apply to the group " + g.getmName() + " ?");
                    builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApplyGroupTask mTask = new ApplyGroupTask(g, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(Bundle extras) {
                                    String message;
                                    boolean bool = extras.getBoolean("success");
                                    if (bool) {
                                        message = "You have been successfully applied to the group";
                                    } else {
                                        message = "There is a problem with the server, please try again later";
                                    }
                                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                                    Activity activity = (Activity) mContext;
                                    activity.finish();
                                }
                            });
                            mTask.execute();
                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.setCancelable(true);
                    builder.show();
                }
            }
        });

        view.setBackground(mContext.getResources().getDrawable(R.drawable.style_groupitem));
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, (int) (150 + description.length() * 0.9)));

        return view;
    }
}
