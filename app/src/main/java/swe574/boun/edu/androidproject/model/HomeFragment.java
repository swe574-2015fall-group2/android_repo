package swe574.boun.edu.androidproject.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Jongaros on 12/4/2015.
 */
public class HomeFragment extends Fragment {
    protected static final String USER_TOKEN = "user";
    protected User mUser;

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user ID Of the user.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_TOKEN, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(USER_TOKEN);
        }
    }
}
