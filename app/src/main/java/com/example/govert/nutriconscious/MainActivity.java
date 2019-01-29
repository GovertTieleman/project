package com.example.govert.nutriconscious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private UserDataBaseHelper dbUser;
    private FoodDatabaseHelper dbFood;
    private ArrayList<FoodItem> foodItems;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get cursors
        dbUser = UserDataBaseHelper.getInstance(this);
        Cursor cursorUser = dbUser.selectUser();
        dbFood = FoodDatabaseHelper.getInstance(this);

//        foodItems = FoodItem.getFoodsFromCursor(dbFood.selectFoods());
//        Cursor cursorFood = dbFood.selectFoods();


        // make instance of user class using cursor
        if (!checkForUser(cursorUser)) {
            startActivity(new Intent(MainActivity.this, PlanActivity.class));
            finish();
        }

        // get foodItems


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
