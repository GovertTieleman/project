package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanActivity extends AppCompatActivity {
    private String ImgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // get radioButtons
        RadioGroup radioGroupActivity = (RadioGroup) findViewById(R.id.radioGroupActivity);
        RadioGroup radioGroupGoal = (RadioGroup) findViewById(R.id.radioGroupGoal);

        // set listener
        radioGroupActivity.setOnCheckedChangeListener(new OnCheckedChangeListener());
        radioGroupGoal.setOnCheckedChangeListener(new OnCheckedChangeListener());

    }

    public void makePlan(View view) {
        // get height, weight and age from views
        EditText heightForm = findViewById(R.id.editTextHeight);
        Integer height = Integer.parseInt(heightForm.getText().toString());

        EditText weightForm = findViewById(R.id.editTextWeight);
        Integer weight = Integer.parseInt(weightForm.getText().toString());

        EditText ageForm = findViewById(R.id.editTextAge);
        Integer age = Integer.parseInt(ageForm.getText().toString());

        // create entry object
        User user = new User(null, gender, height, weight, age, activity, goal);

        // get db
        EntryDatabaseHelper db = EntryDatabaseHelper.getInstance(this.getApplicationContext());

        // insert into db
        db.insert(entry);

        // go back to MainActivity
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private class OnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton1:
                    ImgId = "happier";
                    break;
                case R.id.radioButton2:
                    ImgId = "happy";
                    break;
                case R.id.radioButton3:
                    ImgId = "sad";
                    break;
                case R.id.radioButton4:
                    ImgId = "sadder";
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
