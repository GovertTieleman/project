package com.example.govert.nutriconscious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailRequest.Callback {
    private TextView name;
    private EditText amount;
    private Button add;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get TextViews
        name = (TextView) findViewById(R.id.textViewName);
        amount = (EditText) findViewById(R.id.editTextAmount);
        add = (Button) findViewById(R.id.buttonAdd);
        lv = (ListView) findViewById(R.id.listViewNutrients);

        // get foodItem
        FoodItem foodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");

        // create url
        String url = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=" + foodItem.getNdbno() +
                "&type=f&format=json&api_key=yEpge2Dj4lDU3WIzP5n6vTNGxXZ8uUDJeOhlrVYm";

        // request search
        DetailRequest x = new DetailRequest(this);
        x.getDetails(this, url);
    }

    public void addToDiary(View view) {
    }

    @Override
    public void gotDetails(FoodItem food) {
        // set name
        name.setText(food.getName());

        // make adapter
        NutrientAdapter adapter = new NutrientAdapter(this, 0, food.getNutrients());

        // set adapter
        lv.setAdapter(adapter);
    }

    @Override
    public void gotDetailsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
