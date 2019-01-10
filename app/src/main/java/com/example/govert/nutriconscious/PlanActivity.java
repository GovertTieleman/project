package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class PlanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String gender, activity, goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // get spinner options
        String[] activityLevels = new String[]{"sedentary", "normal", "active"};
        String[] goals = new String[]{"loss", "maintain", "gain"};

        // make activity level spinner
        Spinner spinnerActivity = (Spinner) findViewById(R.id.spinnerActivity);
        ArrayAdapter<String> adapterActivity = new ArrayAdapter<>(PlanActivity.this,
                R.layout.support_simple_spinner_dropdown_item, activityLevels);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerActivity.setAdapter(adapterActivity);

        // make goal spinner
        Spinner spinnerGoals = (Spinner) findViewById(R.id.spinnerGoal);
        ArrayAdapter<String> adapterGoals = new ArrayAdapter<>(PlanActivity.this,
                R.layout.support_simple_spinner_dropdown_item, goals);
        adapterGoals.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGoals.setAdapter(adapterGoals);

        // set listener for spinner
        spinnerActivity.setOnItemSelectedListener(this);
        spinnerGoals.setOnItemSelectedListener(this);

        // get radioGroup
        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);

        // set listener for radioGroup
        radioGroupGender.setOnCheckedChangeListener(new OnCheckedChangeListener());
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
        UserDatabase db = UserDatabase.getInstance(this.getApplicationContext());

        // insert into db
        db.insert(user);

        // go back to MainActivity
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        switch (parentId) {
            case R.id.spinnerActivity:
                switch (position) {
                    case 0:
                        activity = "sedentary";
                        break;
                    case 1:
                        activity = "normal";
                        break;
                    case 2:
                        activity = "active";
                        break;
                }
                break;
            case R.id.spinnerGoal:
                switch (position) {
                    case 0:
                        goal = "loss";
                        break;
                    case 1:
                        goal = "maintain";
                        break;
                    case 2:
                        goal = "gain";
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // auto generated method stub
    }

    private class OnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.male:
                    gender = "male";
                    break;
                case R.id.female:
                    gender = "female";
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
