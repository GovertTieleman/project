package com.example.govert.nutriconscious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private UserDatabase db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get cursor
        db = UserDatabase.getInstance(this);
        Cursor cursor = db.selectUser();

        // make instance of user class using cursor
        if (!getUser(cursor)) {
            startActivity(new Intent(MainActivity.this, PlanActivity.class));
        }

        // get views
        TextView caloriesTextView = findViewById(R.id.textViewCalories);
        String caloriesString = String.format("Calories: 0/%s", user.getCalories().toString());
        caloriesTextView.setText(caloriesString);
    }

    public boolean getUser(Cursor cursor) {
        // check if user exists and make instance of user class if it does
        if (cursor.moveToFirst()) {
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            int height = cursor.getInt(cursor.getColumnIndex("height"));
            int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String activity = cursor.getString(cursor.getColumnIndex("activity"));
            String goal = cursor.getString(cursor.getColumnIndex("goal"));

            user = new User(null, gender, height, weight, age, activity, goal);

            return true;
        }

        // return false if no user exists
        return false;
    }

    public void searchClicked(View view) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    public void planClicked(View view) {
        startActivity(new Intent(MainActivity.this, PlanActivity.class));
    }
}
