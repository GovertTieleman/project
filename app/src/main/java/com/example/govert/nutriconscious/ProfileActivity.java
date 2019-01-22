package com.example.govert.nutriconscious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements Dialog.DialogListener {
    private User user;
    private ListView lv;
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroudActivityGoal;
    private TextView textViewActivityGoal;
    private TextView textViewHeightWeightAge;
    private EditText editTextHeightWeightAge;
    private RadioButton r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get user from db
        UserDataBaseHelper db = UserDataBaseHelper.getInstance(this);
        Cursor cursor = db.selectUser();
        user = User.getUser(cursor);

        // get views
        lv = (ListView) findViewById(R.id.userListView);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioGroudActivityGoal = (RadioGroup) findViewById(R.id.radioGroupActivityGoal);
        textViewActivityGoal = (TextView) findViewById(R.id.textViewDialogActivityGoal);
        textViewHeightWeightAge = (TextView) findViewById(R.id.textViewHeightWeightAge);
        editTextHeightWeightAge = (EditText) findViewById(R.id.editTextHeightWeightAge);

        // set views
        this.setViews();

        // set listener
        lv.setOnItemClickListener(new ListItemClickListener());
    }

    private void setViews() {
        // get fields
        ArrayList<String> userInfo = new ArrayList<>();
        userInfo.add(String.format(Locale.getDefault(), "Gender_%s", user.getGender()));
        userInfo.add(String.format(Locale.getDefault(), "Height_%d", user.getHeight()));
        userInfo.add(String.format(Locale.getDefault(), "Weight_%d", user.getWeight()));
        userInfo.add(String.format(Locale.getDefault(), "Age_%d", user.getAge()));
        userInfo.add(String.format(Locale.getDefault(), "Activity level_%s lifestyle", user.getActivity()));
        userInfo.add(String.format(Locale.getDefault(), "Your weight goal_%s weight", user.getGoal()));

        // make adapter
        ProfileAdapter adapter = new ProfileAdapter(this, 0, userInfo);

        // set adapter
        lv.setAdapter(adapter);
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(), "my_dialog");
        }
    }

    @Override
    public void applyChange(String change) {
        // get update
        String field = change.split("_")[0];
        String value = change.split("_")[1];

        // apply update
        switch (field) {
            case "gender":
                user.setGender(value);
        }

        // update views
        this.setViews();
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
