package com.example.govert.nutriconscious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, ArrayList<FoodItem>> foodItems;
    private User user;
    private Float totalKCal;
    private ArrayList<String> daysOfWeek;
    private DateFormat df;
    private TextView tvTotalKCal, tvAvgKCal, tvWeek;
    private int weekOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        tvTotalKCal = findViewById(R.id.caloriesTotal);
        tvAvgKCal = findViewById(R.id.caloriesAverage);
        tvWeek = findViewById(R.id.tvWeek);

        // get user
        UserDataBaseHelper dbUser = UserDataBaseHelper.getInstance(this);
        if (!checkForUser(dbUser.selectUser())) {
            startActivity(new Intent(MainActivity.this, PlanActivity.class));
            finish();
        }

        // create DateFormat
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // set weekOffset to 0
        weekOffset = 0;

        // set views
        setViews();
    }

    private void setViews() {
        // get days of week
        daysOfWeek = getDaysOfWeek();

        // get foods of week
        foodItems = new HashMap<>();

        // get db
        FoodDatabaseHelper db = FoodDatabaseHelper.getInstance(this);

        // iterate over days
        for (String date : daysOfWeek) {
            // refresh cursor
            Cursor cursor = db.selectFoods();

            // add day of food
            foodItems.put(date, FoodItem.getFoodsFromCursor(cursor, date));
        }

        // get total KCal
        totalKCal = 0f;
        for (String date  : daysOfWeek) {
            ArrayList<FoodItem> foodsForDay = foodItems.get(date);
            for (FoodItem foodItem : foodsForDay) {
                totalKCal += (foodItem.getCalories() * foodItem.getServingQTY());
            }
        }

        // set views
        tvWeek.setText(String.format(Locale.getDefault(), "Week of %s", daysOfWeek.get(0)));
        tvTotalKCal.setText(String.format(Locale.getDefault(), "Total calories this week: %.0f / %.0f", totalKCal, (user.getCalories()*7)));
        tvAvgKCal.setText(String.format(Locale.getDefault(), "Average calories per day: %.0f / %.0f", (totalKCal / 7), user.getCalories()));
    }

    private ArrayList<String> getDaysOfWeek() {
        // make list of dates
        ArrayList<String> dates = new ArrayList<>();

        // get calendar
        Calendar calendar = Calendar.getInstance();

        // set calendar to monday of current week
        int dayOfWeek = 2 - calendar.get(Calendar.DAY_OF_WEEK) + weekOffset;
        calendar.add(Calendar.DATE, dayOfWeek);

        // get days of week
        for (int i = 0; i < 7; i++) {
            // add date
            dates.add(df.format(calendar.getTime()));

            // increment date by 1
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
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

    public void nextClicked(View view) {
        // update dateOffset
        weekOffset += 7;

        // set Views
        setViews();
    }

    public void previousClicked(View view) {
        // update dateOffset
        weekOffset -= 7;

        // set Views
        setViews();
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
