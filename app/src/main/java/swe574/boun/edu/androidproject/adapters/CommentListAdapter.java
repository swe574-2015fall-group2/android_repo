package swe574.boun.edu.androidproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Map;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.Comment;
import swe574.boun.edu.androidproject.model.Image;
import swe574.boun.edu.androidproject.model.User;

/**
 * Created by Jongaros on 1/2/2016.
 */
public class CommentListAdapter extends BaseAdapter {
    private ListViewAdapterListener mListener;
    private Map<User, Comment> mUserCommentMap;
    private Context mContext;

    public CommentListAdapter(ListViewAdapterListener mListener, Map<User, Comment> mUserCommentMap, Context mContext) {
        this.mListener = mListener;
        this.mUserCommentMap = mUserCommentMap;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mUserCommentMap.size();
    }

    @Override
    public Object getItem(int position) {
        int counter = 0;
        for (Map.Entry entry : mUserCommentMap.entrySet()) {
            if (counter == position) {
                return entry;
            }
            counter++;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.comment_list_item, null, false);

        Map.Entry<User, Comment> entry = (Map.Entry<User, Comment>) getItem(position);
        User user = entry.getKey();
        Comment comment = entry.getValue();

        ImageView commentUserPictureImageView = (ImageView) viewGroup.findViewById(R.id.commentUserPictureImageView);
        Bitmap bitmap = null;
        if (user.getmImage() != null) {
            Image image = user.getmImage();
            if (image.getmImage() != null) {
                bitmap = image.getmImage();
                commentUserPictureImageView.setImageBitmap(bitmap);
            }
        }

        TextView commentTextView = (TextView) viewGroup.findViewById(R.id.commentTextView);
        String commentText = null;
        if (comment.getComment() != null) {
            commentText = comment.getComment();
            commentTextView.setText(commentText);
        }

        TextView commentUserDetailsTextView = (TextView) viewGroup.findViewById(R.id.commentUserDetailsTextView);
        String detailsText = "";
        if (user.getmName() != null) {
            detailsText += user.getmName() + " ";
        }
        if (user.getmSurname() != null) {
            detailsText += user.getmSurname();
        }
        commentUserDetailsTextView.setText(detailsText);

        TextView commentDateTextView = (TextView) viewGroup.findViewById(R.id.commentDateTextView);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM HH:mm");
        commentDateTextView.setText(simpleDateFormat.format(comment.getCreationTime()));
        if (mListener != null) {
            mListener.onViewCreated(viewGroup);
        }
        return viewGroup;
    }


}
