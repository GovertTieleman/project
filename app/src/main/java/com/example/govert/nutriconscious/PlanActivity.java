package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PlanActivity extends AppCompatActivity {
    private String gender, activity, goal;
    private int weight, height, age;
    private int stage;
    private TextView tvPlan;
    private EditText etPlan;
    private RadioGroup rg;
    private RadioButton rb1, rb2, rb3;
    private FloatingActionButton backNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        // set the stage ;)
        stage = 0;

        // get radioGroup and buttons
        rg = (RadioGroup) findViewById(R.id.radioGroupPlan);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);

        // get views
        tvPlan = (TextView) findViewById(R.id.textViewPlan);
        etPlan = (EditText) findViewById(R.id.editTextPlan);
        backNav = (FloatingActionButton) findViewById(R.id.backNav);

        setViews();
    }

    public void setViews() {
        // make everything invisible first
        rg.setVisibility(View.INVISIBLE);
        rg.clearCheck();
        rb1.setVisibility(View.INVISIBLE);
        rb2.setVisibility(View.INVISIBLE);
        rb3.setVisibility(View.INVISIBLE);
        tvPlan.setVisibility(View.INVISIBLE);
        etPlan.setVisibility(View.INVISIBLE);
        backNav.show();
        etPlan.setText("");

        // show the right views
        switch (stage) {
            case 0:
                // set visibility
                tvPlan.setVisibility(View.VISIBLE);
                etPlan.setVisibility(View.VISIBLE);
                backNav.hide();

                // set text
                tvPlan.setText("Height in cm");
                if (height != 0) {
                    etPlan.setText(String.format(Locale.getDefault(), "%d", height));
                    etPlan.setSelection(etPlan.getText().length());
                }

                break;
            case 1:
                // set visibility
                tvPlan.setVisibility(View.VISIBLE);
                etPlan.setVisibility(View.VISIBLE);

                // set text
                tvPlan.setText("Weight in kg");
                if (weight != 0) {
                    etPlan.setText(String.format(Locale.getDefault(), "%d", weight));
                    etPlan.setSelection(etPlan.getText().length());
                }

                break;
            case 2:
                // set visibility
                tvPlan.setVisibility(View.VISIBLE);
                etPlan.setVisibility(View.VISIBLE);

                // set text
                tvPlan.setText("Age");
                if (age != 0) {
                    etPlan.setText(String.format(Locale.getDefault(), "%d", age));
                    etPlan.setSelection(etPlan.getText().length());
                }

                break;
            case 3:
                // set visibility
                rg.setVisibility(View.VISIBLE);
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);

                // set text
                rb1.setText("Male");
                rb2.setText("Female");

                break;
            case 4:
                // set visibility
                rg.setVisibility(View.VISIBLE);
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.VISIBLE);

                // set text
                rb1.setText("Sedentary");
                rb2.setText("normal");
                rb3.setText("Active");

                // set checked
                break;
            case 5:
                // set visibility
                rg.setVisibility(View.VISIBLE);
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.VISIBLE);

                // set text
                rb1.setText("Lose weight");
                rb2.setText("Maintain current weight");
                rb3.setText("Gain weight");
                break;
            case 6:
                // set visibility
                tvPlan.setVisibility(View.VISIBLE);

                // set text
                tvPlan.setText("Finished making plan. Click continue to start working " +
                        "on your goal!");
                break;
        }
    }

    public void makePlan() {
        // create entry object
        User user = new User(null, gender, height, weight, age, activity, goal);

        // get db
        UserDataBaseHelper db = UserDataBaseHelper.getInstance(this.getApplicationContext());

        // insert into db
        db.insertUser(user);

        // go to MainActivity
        startActivity(new Intent(PlanActivity.this, MainActivity.class));
        finish();
    }

    public void fabClicked(View view) {
        // check which stage the user is at and do the appropriate action
        switch (stage) {
            case 0:
                try {
                    // get height
                    height = Integer.parseInt(etPlan.getText().toString());

                    // check if realistic
                    if (height >= 80 && height <= 300) {
                        stage = stage + 1;
                        setViews();
                        break;
                    }
                    else {
                        Toast.makeText(this, "Please enter a number between 80 and 300",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Please enter your height in cm",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            case 1:
                try {
                    // get weight
                    weight = Integer.parseInt(etPlan.getText().toString());

                    // check if realistic
                    if (weight >= 30 && weight <= 1000) {
                        stage = stage + 1;
                        setViews();
                        break;
                    }
                    else {
                        Toast.makeText(this, "Please enter a number between 30 and " +
                                        "1000", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Please enter your weight in kg",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            case 2:
                try {
                    // get age
                    age = Integer.parseInt(etPlan.getText().toString());

                    // check if realistic
                    if (age >= 1 && age <= 150) {
                        stage = stage + 1;
                        setViews();
                        break;
                    }
                    else {
                        Toast.makeText(this, "Please enter a number between 1 and 150",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Please enter your age in years",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            case 3:
                if (rb1.isChecked()) {
                    gender = "male";
                    stage = stage + 1;
                    setViews();
                    break;
                } else if (rb2.isChecked()) {
                    gender = "female";
                    stage = stage + 1;
                    setViews();
                    break;
                } else {
                    Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show();
                    break;
                }
            case 4:
                if (rb1.isChecked()) {
                    activity = "sedentary";
                    stage = stage + 1;
                    setViews();
                    break;
                } else if (rb2.isChecked()) {
                    activity = "normal";
                    stage = stage + 1;
                    setViews();
                    break;
                } else if (rb3.isChecked()) {
                    activity = "active";
                    stage = stage + 1;
                    setViews();
                    break;
                } else {
                    Toast.makeText(this, "Please select your level of activity", Toast.LENGTH_LONG).show();
                    break;
                }
            case 5:
                if (rb1.isChecked()) {
                    goal = "lose";
                    stage = stage + 1;
                    setViews();
                    break;
                } else if (rb2.isChecked()) {
                    goal = "maintain";
                    stage = stage + 1;
                    setViews();
                    break;
                } else if (rb3.isChecked()) {
                    goal = "gain";
                    stage = stage + 1;
                    setViews();
                    break;
                } else {
                    Toast.makeText(this, "Please select your weight goal", Toast.LENGTH_LONG).show();
                    break;
                }
            case 6:
                makePlan();
        }
    }

    @Override
    public void onBackPressed() {
        if (stage == 0) {
            finish();
        }
        else {
            stage = stage - 1;
            setViews();
        }
    }

    public void backClicked(View view) {
        onBackPressed();
    }
}
