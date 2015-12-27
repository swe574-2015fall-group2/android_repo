package swe574.boun.edu.androidproject.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import swe574.boun.edu.androidproject.NewDiscussionActivity;
import swe574.boun.edu.androidproject.NewNoteActivity;
import swe574.boun.edu.androidproject.R;
import swe574.boun.edu.androidproject.ViewDiscussionActivity;
import swe574.boun.edu.androidproject.ViewNoteActivity;
import swe574.boun.edu.androidproject.message.App;
import swe574.boun.edu.androidproject.model.Discussion;
import swe574.boun.edu.androidproject.model.ModelFragment;
import swe574.boun.edu.androidproject.model.Note;
import swe574.boun.edu.androidproject.network.JSONRequest;

public class NoteTabFragment extends ModelFragment {
    private ListView mNotesListView;
    private List<Note> mNotesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_note, null, false);

        mNotesListView = (ListView) viewGroup.findViewById(R.id.listNotes);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("authToken", App.mAuth);
            jsonObject.accumulate("id", mGroup.getmID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONRequest jsonRequest = new JSONRequest("http://162.243.215.160:9000/v1/note/queryByGroup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.has("status")) {
                        String status = responseObject.getString("status");
                        if (status.equals("success")) {
                            mNotesList = new ArrayList<>();
                            responseObject = responseObject.getJSONObject("result");
                            if (responseObject.has("noteList")) {
                                JSONArray jsonArray = responseObject.getJSONArray("noteList");
                                GsonBuilder builder = new GsonBuilder();
                                builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                        return new Date(json.getAsJsonPrimitive().getAsLong());
                                    }
                                });
                                Gson gson = builder.create();
                                ArrayList<String> adapterHolder = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    mNotesList.add(gson.fromJson(object.toString(), Note.class));
                                    adapterHolder.add(mNotesList.get(i).getTitle());
                                }
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, adapterHolder);
                                mNotesListView.setAdapter(arrayAdapter);
                                mNotesListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Note note = mNotesList.get(position);
                                        Intent intent = new Intent(getContext(), ViewNoteActivity.class);
                                        intent.putExtra("note", note);
                                        startActivityForResult(intent, 2);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mNotesListView.getAdapter() == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_nogroups, R.id.textview, new String[]{"No notes are are found. Press here to create a note."});
                    mNotesListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ;
                            Intent intent = new Intent(getContext(), NewNoteActivity.class);
                            startActivityForResult(intent, 1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    mNotesListView.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, jsonObject);
        requestQueue.add(jsonRequest);
        return viewGroup;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                getActivity().setTitle("Notes");
            }
        }
    }

}
