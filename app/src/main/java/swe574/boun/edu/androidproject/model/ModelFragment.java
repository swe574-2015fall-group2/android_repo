package swe574.boun.edu.androidproject.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Jongaros on 12/4/2015.
 */
public class ModelFragment extends Fragment {
    private final static String GROUP_TOKEN = "group";
    private final static String USER_TOKEN = "user";
    protected Group mGroup;
    protected User mUser;

    public ModelFragment(){
        // Required empty public constructor
    }

    public static ModelFragment newInstance(final Group GROUP, final User user) {
        ModelFragment fragment = new ModelFragment();
        Bundle args = new Bundle();
        args.putParcelable(GROUP_TOKEN, GROUP);
        args.putParcelable(USER_TOKEN, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(USER_TOKEN);
            mGroup = getArguments().getParcelable(GROUP_TOKEN);
        }
    }


}
