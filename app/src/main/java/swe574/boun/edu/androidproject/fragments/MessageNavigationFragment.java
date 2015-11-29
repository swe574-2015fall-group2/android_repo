package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import swe574.boun.edu.androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageNavigationFragment extends Fragment {
    // Fragment parameters.
    private final static String USER_TOKEN = "user";
    private String USER_ID;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_id ID Of the user.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageNavigationFragment newInstance(String user_id) {
        MessageNavigationFragment fragment = new MessageNavigationFragment();
        Bundle args = new Bundle();
        args.putString(USER_TOKEN, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            USER_ID = (String) getArguments().get(USER_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_message, container, false);
        ListView peopleList = (ListView) rootView.findViewById(R.id.listViewPeople);
        // TODO Implement this after service
        //peopleList.setAdapter(new PeopleListAdapter(list));
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle("Messages");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Messages");
    }
}
