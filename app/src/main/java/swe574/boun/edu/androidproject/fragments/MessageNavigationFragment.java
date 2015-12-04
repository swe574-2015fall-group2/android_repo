package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.model.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageNavigationFragment extends HomeFragment {

    public MessageNavigationFragment() {
        // Required empty public constructor
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
