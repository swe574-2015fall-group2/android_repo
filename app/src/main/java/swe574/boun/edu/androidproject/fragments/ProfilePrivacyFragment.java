package swe574.boun.edu.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import swe574.boun.edu.androidproject.R;

/**
 * Created by Jongaros on 12/20/2015.
 */
public class ProfilePrivacyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile_privacy, null, false);

        final TextView privacyText = (TextView) viewGroup.findViewById(R.id.privacyText);
        privacyText.setText("Everyone can send you messages.");
        ToggleButton privacyButton = (ToggleButton) viewGroup.findViewById(R.id.tooglePrivacy);
        privacyButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    privacyText.setText("Everyone can send you messages.");
                } else {
                    privacyText.setText("No one can send you messages.");
                }
            }
        });

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
}
