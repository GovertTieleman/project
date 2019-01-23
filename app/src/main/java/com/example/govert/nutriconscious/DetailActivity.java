package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.govert.nutriconscious.FoodItem.makeDate;

public class DetailActivity extends AppCompatActivity implements DetailRequest.Callback {
    private TextView name, servingSize, KCal;
    private EditText numberEdit;
    private FoodItem detailedFood;
    private FoodItemSimple selectedFood;
    private ListView lv;
    private Float numberOfServings;
    private String source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get Views
        name = (TextView) findViewById(R.id.textViewName);
        servingSize = (TextView) findViewById(R.id.textViewServingSizeUnit);
        KCal = (TextView) findViewById(R.id.textViewAmountOfKCal);
        lv = (ListView) findViewById(R.id.listViewNutrients);
        numberEdit = (EditText) findViewById(R.id.editTextAmount);

        // get source
        source = getIntent().getStringExtra("source");

        // get FoodItemSimple if source = search
        if (source.equals("search")) {
            // set selectedFood
            selectedFood = (FoodItemSimple) getIntent().getSerializableExtra("foodItem");

            // set number of servings
            numberOfServings = selectedFood.getServingQTY();

            // create url
            String url = "https://api.nutritionix.com/v1_1/item?id=" + selectedFood.getIdAPI() +
                    "&appId=3f320916&appKey=fc58ccfd02cc5e1d32acce42ecee8bf6";

            // request search and setNutrients from response handler
            DetailRequest x = new DetailRequest(this);
            x.getDetails(this, url, selectedFood);
        }

        // get regular FoodItem if source = diary
        else {
            // set detailedFood
            detailedFood = (FoodItem) getIntent().getSerializableExtra("foodItem");

            // set number of servings
            numberOfServings = detailedFood.getServingQTY();

            // setNutrients
            setNutrients();

            // set numberEdit listener
            this.setListener();
        }

        // set views
        this.setViews();
    }

    public void setViews() {
        // set numberOfServings
        numberEdit.setText(String.format(Locale.getDefault(), "%.2f", numberOfServings));
        numberEdit.setSelection(numberEdit.getText().length());


        // use selectedFood or detailedFood depending on source
        if (source.equals("search")) {
            // set name and servingSize
            name.setText(selectedFood.getName());
            servingSize.setText(String.format("1 %s", selectedFood.getServingSize()));

            // set calories per serving
            String calories = String.format(Locale.getDefault(), "%.2f",
                    selectedFood.getCalories());
            KCal.setText(calories);
        }
        else {
            // set name and servingSize
            name.setText(detailedFood.getName());
            servingSize.setText(String.format("1 %s", detailedFood.getServingSize()));

            // set calories per serving
            String calories = String.format(Locale.getDefault(), "%.2f",
                    detailedFood.getCalories());
            KCal.setText(calories);
        }
    }

    private void setNutrients() {
        // initialize ArrayList of nutrients
        ArrayList<Nutrient> nutrients = new ArrayList<>();

        // add calories, protein, carbohydrate and fat
        nutrients.add(new Nutrient("calories", "KCal",
                detailedFood.getCalories() * numberOfServings));
        nutrients.add(new Nutrient("protein", "g",
                detailedFood.getProtein() * numberOfServings));
        nutrients.add(new Nutrient("carbohydrate", "g",
                detailedFood.getCarbohydrate() * numberOfServings));
        nutrients.add(new Nutrient("fat", "g",
                detailedFood.getFat() * numberOfServings));

        // make adapter
        NutrientAdapter adapter = new NutrientAdapter(this, 0, nutrients);

        // set adapter
        lv.setAdapter(adapter);
    }

    private void changeNutrients(CharSequence s) {
        try {
            numberOfServings = Float.valueOf(s.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (numberOfServings > 999999) {
            numberOfServings = 0f;

            numberEdit.setText("");

            Toast.makeText(this, "Number too high", Toast.LENGTH_LONG).show();
        }

        this.setNutrients();
    }

    public void fabClicked(View view) {
        // get date and set date
        String date = makeDate();
        detailedFood.setDate(date);

        // set number of servings for detailedFood
        detailedFood.setServingQTY(numberOfServings);

        // get db
        FoodDatabaseHelper db = FoodDatabaseHelper.getInstance(this.getApplicationContext());

        // insert or update food depending on source
        if (source.equals("search")) {
            db.insertFood(detailedFood);
        }
        else {
            db.updateFood(detailedFood);
        }

        // broadcast to search
        Intent intent = new Intent("finish");
        sendBroadcast(intent);

        // return to main
        startActivity(new Intent(this, DiaryActivity.class));
        finish();
    }

    @Override
    public void gotDetails(FoodItem foodItem) {
        detailedFood = foodItem;
        this.setNutrients();

        // set listener for numberEdit (after getting API response to prevent bugs)
        this.setListener();
    }

    @Override
    public void gotDetailsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setListener() {
        numberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeNutrients(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Auto-generated method stub
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailActivity.this, DiaryActivity.class));
        finish();
    }
}
