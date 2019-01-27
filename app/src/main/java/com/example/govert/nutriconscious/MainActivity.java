package com.example.govert.nutriconscious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private UserDataBaseHelper db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get cursor
        db = UserDataBaseHelper.getInstance(this);
        Cursor cursor = db.selectUser();

        // make instance of user class using cursor
        if (!checkForUser(cursor)) {
            startActivity(new Intent(MainActivity.this, PlanActivity.class));
            finish();
        }

        // get views
        TextView caloriesTextView = findViewById(R.id.textViewCalories);
        String caloriesString = String.format("Calories: 0/%s", user.getCalories().toString());
        caloriesTextView.setText(caloriesString);
    }

    public boolean checkForUser(Cursor cursor) {
        // check if user exists and make instance of user class if it does
        if (cursor.moveToFirst()) {
            // set user
            user = User.getUser(cursor);
            return true;
        }

        // return false if no user exists
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public void searchClicked(View view) {
        // make intent
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);

        // put source
        intent.putExtra("source", "main");
        intent.putExtra("dateOffset", 0);

        // startActivity
        startActivity(intent);
        finish();
    }

    public void diaryClicked(View view) {
        startActivity(new Intent(MainActivity.this, DiaryActivity.class));
        finish();
    }

    public void homeClicked(View view) {
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();
    }

    public void profileClicked(View view) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        finish();
    }
}
