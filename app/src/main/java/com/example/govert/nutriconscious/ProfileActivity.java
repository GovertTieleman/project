package com.example.govert.nutriconscious;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    private ListView lv;
    private EditText et;
    private TextView tv;
    private RadioButton rb1, rb2, rb3;
    private UserDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get db
        db = UserDataBaseHelper.getInstance(this);

        // get views
        lv = (ListView) findViewById(R.id.userListView);
        tv = (TextView) findViewById(R.id.goal);

        // set views
        this.setViews();

        // set listener
        lv.setOnItemClickListener(new ListItemClickListener());
    }

    private void setViews() {
        // get user
        user = User.getUser(db.selectUser());

        // get fields
        ArrayList<String> userInfo = new ArrayList<>();
        userInfo.add(String.format(Locale.getDefault(), "Height_%d", user.getHeight()));
        userInfo.add(String.format(Locale.getDefault(), "Weight_%d", user.getWeight()));
        userInfo.add(String.format(Locale.getDefault(), "Age_%d", user.getAge()));
        userInfo.add(String.format(Locale.getDefault(), "Gender_%s", user.getGender()));
        userInfo.add(String.format(Locale.getDefault(), "Activity level_%s lifestyle",
                user.getActivity()));
        userInfo.add(String.format(Locale.getDefault(), "Your weight goal_%s weight",
                user.getGoal()));

        // make adapter
        ProfileAdapter adapter = new ProfileAdapter(this, 0, userInfo);

        // set adapter
        lv.setAdapter(adapter);

        // get calories goal
        Float calories = (Float) user.getCalories().floatValue();

        // set goal
        tv.setText(String.format(Locale.getDefault(), "Your daily calorie goal is: %.0f",
                calories));
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            // initialize dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

            // inflate view
            LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();

            // declare dialogView as final
            final View dialogView = getView(position, inflater);

            // set buttons
            builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    applyChange(position);
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            // setView
            builder.setView(dialogView);

            // create dialog
            AlertDialog dialog = builder.create();

            // show keyboard if needed
            final int[] array = {0, 1, 2};
            for (final int i : array) {
                if (i == position) {
                    dialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
            }

            // show dialog
            dialog.show();
        }
    }

    private void applyChange(int position) {
        switch (position) {
            case 0:
                // check if user entered a number with try-catch block
                try {
                    // get height
                    int height = Integer.parseInt(et.getText().toString());

                    // check if realistic
                    if (height >= 80 && height <= 300) {
                        // update user
                        user.setHeight(height);
                        break;
                    }
                    else {
                        Toast.makeText(this, "Please enter a number between 80 " +
                                        "and 300",
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
                // check if user entered a number with try-catch block
                try {
                    // get weight
                    int weight = Integer.parseInt(et.getText().toString());

                    // check if realistic
                    if (weight >= 30 && weight <= 1000) {
                        // update user
                        user.setWeight(weight);
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
                // check if user entered a number with try-catch block
                try {
                    // get age
                    int age = Integer.parseInt(et.getText().toString());

                    // check if realistic
                    if (age >= 1 && age <= 150) {
                        // update user
                        user.setAge(age);
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
                // get user choice
                if (rb1.isChecked()) {
                    user.setGender("male");
                    break;
                } else {
                    user.setGender("female");
                    break;
                }
            case 4:
                // get user choice
                if (rb1.isChecked()) {
                    user.setActivity("sedentary");
                    break;
                } else if (rb2.isChecked()) {
                    user.setActivity("normal");
                    break;
                } else {
                    user.setActivity("active");
                    break;
                }
            case 5:
                // get user choice
                if (rb1.isChecked()) {
                    user.setGoal("lose");
                    break;
                } else if (rb2.isChecked()) {
                    user.setGoal("maintain");
                    break;
                } else {
                    user.setGoal("gain");
                    break;
                }
        }

        db.updateUser(user);

        setViews();
    }

    private View getView(int position, LayoutInflater inflater) {
        View view = null;

        // create the right dialog
        switch (position) {
            case 0:
                view = inflater.inflate(R.layout.dialog_height, null);

                et = (EditText) view.findViewById(R.id.editTextDialog);

                et.setText(String.format(Locale.getDefault(), "%d", user.getHeight()));
                et.setSelection(et.getText().length());

                break;
            case 1:
                view =  inflater.inflate(R.layout.dialog_weight, null);

                et = (EditText) view.findViewById(R.id.editTextDialog);

                et.setText(String.format(Locale.getDefault(), "%d", user.getWeight()));
                et.setSelection(et.getText().length());

                break;
            case 2:
                view =  inflater.inflate(R.layout.dialog_age, null);

                et = (EditText) view.findViewById(R.id.editTextDialog);

                et.setText(String.format(Locale.getDefault(), "%d", user.getAge()));
                et.setSelection(et.getText().length());

                break;
            case 3:
                view =  inflater.inflate(R.layout.dialog_gender, null);

                rb1 = (RadioButton) view.findViewById(R.id.rbDialog1);
                rb2 = (RadioButton) view.findViewById(R.id.rbDialog2);

                switch (user.getGender()) {
                    case "male":
                        rb1.setChecked(true);
                        break;
                    case "female":
                        rb2.setChecked(true);
                        break;
                }

                break;
            case 4:
                view =  inflater.inflate(R.layout.dialog_activity, null);

                rb1 = (RadioButton) view.findViewById(R.id.rbDialog1);
                rb2 = (RadioButton) view.findViewById(R.id.rbDialog2);
                rb3 = (RadioButton) view.findViewById(R.id.rbDialog3);

                switch (user.getActivity()) {
                    case "sedentary":
                        rb1.setChecked(true);
                        break;
                    case "normal":
                        rb2.setChecked(true);
                        break;
                    case "active":
                        rb3.setChecked(true);
                        break;
                }

                break;
            case 5:
                view =  inflater.inflate(R.layout.dialog_goal, null);

                rb1 = (RadioButton) view.findViewById(R.id.rbDialog1);
                rb2 = (RadioButton) view.findViewById(R.id.rbDialog2);
                rb3 = (RadioButton) view.findViewById(R.id.rbDialog3);

                switch (user.getGoal()) {
                    case "lose":
                        rb1.setChecked(true);
                        break;
                    case "maintain":
                        rb2.setChecked(true);
                        break;
                    case "gain":
                        rb3.setChecked(true);
                        break;
                }
                break;
        }

        // return dialogView
        return view;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }

    public void diaryClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, DiaryActivity.class));
        finish();
    }

    public void homeClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }

    public void profileClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        finish();
    }
}
