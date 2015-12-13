package swe574.boun.edu.androidproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import swe574.boun.edu.androidproject.model.OnTaskCompleted;
import swe574.boun.edu.androidproject.model.User;
import swe574.boun.edu.androidproject.tasks.UpdateUserProfileTask;

public class UpdateProfileActivity extends AppCompatActivity {
    private Button mUpdateButtonView;
    private AutoCompleteTextView mBirthDateView;
    private UpdateUserProfileTask mTask;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setTitle("Update Profile");

        Bundle extras = getIntent().getExtras();
        mUser = extras.getParcelable("user");

        final TextView nameTextView = (TextView) findViewById(R.id.first_name);
        final TextView surnameTextView = (TextView) findViewById(R.id.last_name);
        final TextView professionTextView = (TextView) findViewById(R.id.textViewProfession);
        final TextView interestTextView = (TextView) findViewById(R.id.textViewInterests);
        final TextView universityTextView = (TextView) findViewById(R.id.textViewUniversity);
        final TextView programmeTextView = (TextView) findViewById(R.id.textViewProgramme);
        TextView birthDateTextView = (TextView) findViewById(R.id.textViewBirthDate);
        final TextView linkedinTextView = (TextView) findViewById(R.id.textViewLinkedin);
        final TextView academiaTextView = (TextView) findViewById(R.id.textViewAcademica);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, monthOfYear);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                mBirthDateView.setText(format.format(calendar.getTime()));
            }
        };
        mBirthDateView = (AutoCompleteTextView) findViewById(R.id.textViewBirthDate);
        mBirthDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateProfileActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mUpdateButtonView = (Button) findViewById(R.id.update_profile_button);
        mUpdateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask = new UpdateUserProfileTask(new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(Bundle extras) {
                        boolean success = extras.getBoolean("success");
                        if (success) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }
                }, mUser.getmID(), nameTextView.getText().toString(), surnameTextView.getText().toString(), calendar.getTime(), professionTextView.getText().toString(), programmeTextView.getText().toString(), universityTextView.getText().toString(), interestTextView.getText().toString(), linkedinTextView.getText().toString(), academiaTextView.getText().toString());
                mTask.execute();
            }
        });

    }
}
