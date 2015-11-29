package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import swe574.boun.edu.androidproject.LoginActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ResourcesActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileNavigationFragment extends Fragment {
    // Fragment parameters.
    private final static String USER_TOKEN = "user";
    private String USER_ID;
    private int EDIT_MENU_ID;

    public ProfileNavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_id ID Of the user.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileNavigationFragment newInstance(String user_id) {
        ProfileNavigationFragment fragment = new ProfileNavigationFragment();
        Bundle args = new Bundle();
        args.putString(USER_TOKEN, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            USER_ID = (String) getArguments().get(USER_TOKEN);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.profilePicture);
        ViewGroup profilePicture = (ViewGroup) inflater.inflate(R.layout.profilepicture, null, false);
        frameLayout.addView(profilePicture);

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
                intent.putExtra("user", USER_ID);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        EDIT_MENU_ID = menu.FIRST + 1;
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
