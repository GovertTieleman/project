package com.example.govert.nutriconscious;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        // set offset
        dateOffset = getIntent().getIntExtra("dateOffset", 0);

        // set views
        setViews();
    }

    private void setViews() {
        // get date
        String dateSelected = makeDate(dateOffset);

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
                tvDate.setText(dateSelected);
                break;
        }

        // get food db
        dbFood = FoodDatabaseHelper.getInstance(this);

        // get foodItems
        Cursor cursorFood = dbFood.selectFoods();
        foodItems = getFoodsFromCursor(cursorFood, dateSelected);

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

            // start MenuActivity with intent
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
                setViews();
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
        dateOffset += 1;
        setViews();
    }

    public void previousClicked(View view) {
        dateOffset -= 1;
        setViews();
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
