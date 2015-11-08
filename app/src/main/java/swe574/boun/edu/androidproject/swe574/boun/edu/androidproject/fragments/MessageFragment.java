package swe574.boun.edu.androidproject.swe574.boun.edu.androidproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.swe574.boun.edu.androidproject.message.adapters.PeopleListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
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
    public static MessageFragment newInstance(String user_id) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(USER_TOKEN, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
