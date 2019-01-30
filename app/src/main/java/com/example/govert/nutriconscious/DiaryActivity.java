package com.example.govert.nutriconscious;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.govert.nutriconscious.FoodItem.getFoodsFromCursor;
import static com.example.govert.nutriconscious.FoodItem.makeDate;

public class DiaryActivity extends AppCompatActivity {

    private TextView tvDate, caloriesLeft;
    private ListView lv;
    private FoodDatabaseHelper dbFood;
    private ArrayList<FoodItem> foodItems;
    private User user;
    private int dateOffset;
    private Date date;
    private DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        // get views
        tvDate = findViewById(R.id.dayOrMonth);
        caloriesLeft = findViewById(R.id.caloriesLeft);
        lv = findViewById(R.id.diaryList);

        // get user
        UserDataBaseHelper dbUser = UserDataBaseHelper.getInstance(this);
        Cursor cursorUser = dbUser.selectUser();
        user = User.getUser(cursorUser);

        // create DateFormat
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // make dateString
        String dateString;

        // get dateOffset
        dateOffset = getIntent().getIntExtra("dateOffset", 0);

        // get date from intent or calendar
        try {
            date = (Date) getIntent().getSerializableExtra("date");
            dateString = df.format(date);
        }
        catch (Exception e) {
            date = Calendar.getInstance().getTime();
            dateString = df.format(date);
        }

        Log.d("dateisnow", dateString);

        // set views
        setViews(dateString);
    }

    private void setViews(String dateString) {
        // set tvDate
        switch (dateOffset) {
            case 0:
                tvDate.setText("Today");
                break;
            case 1:
                tvDate.setText("Tomorrow");
                break;
            case -1:
                tvDate.setText("Yesterday");
                break;
            default:
                tvDate.setText(dateString);
                break;
        }

        // get food db
        dbFood = FoodDatabaseHelper.getInstance(this);

        // get foodItems
        Cursor cursorFood = dbFood.selectFoods();
        foodItems = getFoodsFromCursor(cursorFood, dateString);

        // get adapter
        DiaryAdapter adapter = new DiaryAdapter(this, 0, foodItems);

        // set DiaryAdapter
        lv.setAdapter(adapter);

        // set listeners
        lv.setOnItemClickListener(new ListItemClickListener());
        lv.setOnItemLongClickListener(new ListItemLongClickListener());

        // set caloriesLeft
        Float caloriesUser = BigDecimal.valueOf(user.getCalories()).floatValue();
        Float caloriesFood = 0f;
        for (FoodItem foodItem : foodItems) {
            caloriesFood += (foodItem.getCalories() * foodItem.getServingQTY());
        }

        caloriesLeft.setText(String.format(Locale.getDefault(), "Calories left: %.0f - " +
                        "%.0f = %.0f", caloriesUser,
                caloriesFood, (caloriesUser - caloriesFood)));
    }

    public void pickDate(View view) {
        // make alertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // inflate view
        LayoutInflater inflater = DiaryActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.date_picker, null);

        // get DatePicker
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        // set View
        alertDialogBuilder.setView(dialogView);

        // create options
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // make calendar
                Calendar calendar = Calendar.getInstance();

                // get currentDate
                Date currentDate = calendar.getTime();

                // set calendar and update datePicker
                calendar.set(datePicker.getYear(), datePicker.getMonth(),
                        datePicker.getDayOfMonth());

                // get date
                date = calendar.getTime();

                // calculate new dateOffset
                if (date.after(currentDate)) {
                    dateOffset = (int) ((date.getTime() - currentDate.getTime()) / 86400000);
                }
                else {
                    dateOffset = (int) - ((currentDate.getTime() - date.getTime()) / 86400000);
                }

                // set Views
                setViews(df.format(date));
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // show dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            deleteFood(foodItems.get(position).getId());
            return true;
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FoodItem foodSelected = foodItems.get(position);

            // make intent
            Intent intent = new Intent(DiaryActivity.this, DetailActivity.class);
            intent.putExtra("foodItem", foodSelected);
            intent.putExtra("source", "diary");
            intent.putExtra("dateOffset", dateOffset);
            intent.putExtra("date", date);

            // start DetailActivity with intent
            startActivity(intent);
            finish();
        }
    }

    public void searchClicked(View view) {
        // make intent
        Intent intent = new Intent(DiaryActivity.this, SearchActivity.class);

        // put source
        intent.putExtra("source", "diary");
        intent.putExtra("dateOffset", dateOffset);
        intent.putExtra("date", date);

        // startActivity
        startActivity(intent);
        finish();
    }

    public void deleteFood(final int id) {
        // make alertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete food from diary?");

        // create options
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dbFood.deleteFood(id);
                setViews(makeDate(dateOffset));
                Toast.makeText(DiaryActivity.this, "Deleted", Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // show dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void nextClicked(View view) {
        // update dateOffset
        dateOffset += 1;

        // update Date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dateOffset);
        date = calendar.getTime();

        setViews(makeDate(dateOffset));
    }

    public void previousClicked(View view) {
        // update dateOffset
        dateOffset -= 1;

        // get Date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dateOffset);
        date = calendar.getTime();

        setViews(makeDate(dateOffset));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DiaryActivity.this, MainActivity.class));
        finish();
    }

    public void diaryClicked(View view) {
        startActivity(new Intent(DiaryActivity.this, DiaryActivity.class));
        finish();
    }

    public void homeClicked(View view) {
        startActivity(new Intent(DiaryActivity.this, MainActivity.class));
        finish();
    }

    public void profileClicked(View view) {
        startActivity(new Intent(DiaryActivity.this, ProfileActivity.class));
        finish();
    }
}
