package com.example.govert.nutriconscious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailRequest.Callback {
    private TextView name, KCal, protein, carb, fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get TextViews
        name = (TextView) findViewById(R.id.textViewName);
        KCal = (TextView) findViewById(R.id.textViewKCal);
        protein = (TextView) findViewById(R.id.textViewProtein);
        carb = (TextView) findViewById(R.id.textViewCarb);
        fat = (TextView) findViewById(R.id.textViewFat);

        // get foodItem
        FoodItem foodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");

        // create url
        String url = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=" + foodItem.getNdbno() +
                "&type=f&format=json&api_key=yEpge2Dj4lDU3WIzP5n6vTNGxXZ8uUDJeOhlrVYm";

        // request search
        DetailRequest x = new DetailRequest(this);
        x.getDetails(this, url);
    }

    @Override
    public void gotDetails(FoodItem food) {
        // set TextViews to show nutrient profile
        name.setText(food.getName());
        KCal.setText(String.format("Calories: %s", food.getKCal()));
        protein.setText(String.format("Protein: %s", food.getProtein()));
        carb.setText(String.format("Carbs: %s", food.getCarb()));
        fat.setText(String.format("Fat: %s", food.getFat()));
    }

    @Override
    public void gotDetailsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
