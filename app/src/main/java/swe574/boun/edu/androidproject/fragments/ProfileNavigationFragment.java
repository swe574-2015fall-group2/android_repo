package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import swe574.boun.edu.androidproject.LoginActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ResourcesActivity;
import swe574.boun.edu.androidproject.model.HomeFragment;
import swe574.boun.edu.androidproject.model.Image;
import swe574.boun.edu.androidproject.model.UserDetails;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link swe574.boun.edu.androidproject.model.HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileNavigationFragment extends HomeFragment {
    // Fragment parameters.
    private int EDIT_MENU_ID;
    // UI parameters
    private ImageView mProfileImageView;
    private TextView mProfileFullName;
    private TextView mProfileUsername;
    private TextView mProfession;
    private TextView mInterests;
    private TextView mUniversity;
    private TextView mProgramme;
    private TextView mBirthDate;
    private TextView mLinkedin;
    private TextView mAcademica;

    public ProfileNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.profilePicture);
        ViewGroup profilePicture = (ViewGroup) inflater.inflate(R.layout.profilepicture, null, false);

        mProfileImageView = (ImageView) profilePicture.findViewById(R.id.imageViewProfilePicture);
        Image image = mUser.getmImage();
        if(image != null){
            byte[] imageArray = Base64.decode(image.getmImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
            mProfileImageView.setImageBitmap(bitmap);
            mProfileImageView.invalidate();
        }

        mProfileFullName = (TextView) profilePicture.findViewById(R.id.textViewPersonName);
        if(mUser.getmName() != null && mUser.getmSurname() != null){
            if(!TextUtils.isEmpty(mUser.getmName()) && !TextUtils.isEmpty(mUser.getmSurname())){
                mProfileFullName.setText(mUser.getmName() + " " + mUser.getmSurname());
            }
            else{
                mProfileFullName.setText("Unknown User");
            }
        }

        mProfileUsername = (TextView) profilePicture.findViewById(R.id.textViewPersonUsername);
        if(mUser.getmUsername() != null){
            if(!TextUtils.isEmpty(mUser.getmUsername())){
                mProfileUsername.setText(mUser.getmUsername());
            }
            else{
                mProfileUsername.setText("");
            }
        }

        frameLayout.addView(profilePicture);

        UserDetails details = mUser.getmDetails();
        mProfession = (TextView) viewGroup.findViewById(R.id.textViewProfession);
        mInterests = (TextView) viewGroup.findViewById(R.id.textViewInterests);
        mUniversity = (TextView) viewGroup.findViewById(R.id.textViewUniversity);
        mProgramme = (TextView) viewGroup.findViewById(R.id.textViewProgramme);
        mBirthDate = (TextView) viewGroup.findViewById(R.id.textViewBirthDate);
        mLinkedin = (TextView) viewGroup.findViewById(R.id.textViewLinkedin);
        mAcademica = (TextView) viewGroup.findViewById(R.id.textViewAcademica);

        if(details != null){
            if(details.getmProfession() != null){
                mProfession.setText(details.getmProfession());
            }

            if(details.getmInterests() != null){
                mInterests.setText(details.getmInterests());
            }

            if(details.getmUniversity() != null){
                mUniversity.setText(details.getmUniversity());
            }

            if(details.getmProgramme() != null){
                mProgramme.setText(details.getmProgramme());
            }

            if(details.getmBirthDate() != null){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = format.format(details.getmBirthDate());
                mBirthDate.setText(date);
            }

            if(details.getmLinkedin() != null){
                mLinkedin.setText(Html.fromHtml("<a href=\"http://" + details.getmLinkedin() + "\">" + details.getmLinkedin() + "</a> "));
                mLinkedin.setMovementMethod(LinkMovementMethod.getInstance());
            }

            if(details.getmAcademia() != null){
                mAcademica.setText(Html.fromHtml("<a href=\"http://" + details.getmAcademia() + "\">" + details.getmAcademia() + "</a> "));
                mAcademica.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }

        Button mDeleteAccount = (Button) viewGroup.findViewById(R.id.buttonRemoveAccount);
        mDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Archive Account");
                builder.setMessage("Are you sure to archive your account?\n\nAs our terms of usage, our company holds all of the rights of the content" +
                        " that is posted, used, shared and uploaded content in our website in whatever form of content it has been used. The content owned by our company will be only used for display purposes only." +
                        " Pressing 'Yes' " +
                        "means that you agree on our terms of account archival.");
                builder.setPositiveButton("Yes, I agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Call the backup service
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });

        Button mResources = (Button) viewGroup.findViewById(R.id.buttonResources);
        mResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResourcesActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        EDIT_MENU_ID = Menu.FIRST + 1;
        menu.add(1, EDIT_MENU_ID, 1, "Edit Profile");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle("My Profile");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("My Profile");
    }
}
