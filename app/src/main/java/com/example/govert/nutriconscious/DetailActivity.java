package com.example.govert.nutriconscious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailRequest.Callback {
    private TextView name, servingSize, KCal;
    private EditText amount;
    private Button add;
    private FoodItem foodItem;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get TextViews
        name = (TextView) findViewById(R.id.textViewName);
        servingSize = (TextView) findViewById(R.id.textViewServingSize);
        amount = (EditText) findViewById(R.id.editTextAmount);
        KCal = (TextView) findViewById(R.id.textViewAmountOfKCal);
        add = (Button) findViewById(R.id.buttonAdd);
        lv = (ListView) findViewById(R.id.listViewNutrients);

        // get foodItem
        foodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");

        // set views
        name.setText(String.format("%s, %d %s", foodItem.getName(), foodItem.getServingQTY(),
                foodItem.getServingSize()));

        servingSize.setText(String.format("Serving Size: 1 %s", foodItem.getServingSize()));

        amount.setText(String.format("%d", foodItem.getServingQTY()));

        KCal.setText(String.format("Amount of KCal: %s", foodItem.getCalories()));

        // create url
        String url = "https://api.nutritionix.com/v1_1/item?id=" + foodItem.getId() +
                "&appId=3f320916&appKey=fc58ccfd02cc5e1d32acce42ecee8bf6";

        // request search
        DetailRequest x = new DetailRequest(this);
        x.getDetails(this, url);
    }

    public void addToDiary(View view) {
    }

    @Override
    public void gotDetails(ArrayList<Nutrient> nutrients) {
        // set name
        name.setText(foodItem.getName());

        // make adapter
        NutrientAdapter adapter = new NutrientAdapter(this, 0, foodItem.getNutrients());

        // set adapter
        lv.setAdapter(adapter);
    }

    @Override
    public void gotDetailsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
