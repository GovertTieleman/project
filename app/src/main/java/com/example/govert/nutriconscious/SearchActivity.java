package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchRequest.Callback {
    private ListView lv;
    private TextView tv;
    private ArrayList<FoodItem> foodsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // get ListView and TextView
        lv = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.textView);

        // set listener
        lv.setOnItemClickListener(new ListItemClickListener());
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FoodItem foodItem = foodsFound.get(position);

            // make intent
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra("foodItem", foodItem);

            // start MenuActivity with intent
            startActivity(intent);
        }
    }

    public void searchClicked(View view) {
        // get search term
        String term = tv.getText().toString();

        // create url
        String url = "https://api.nutritionix.com/v1_1/search/" + term + "?results=0%3A25" +
                "&cal_min=0&cal_max=50000&fields=item_name,item_id,nf_calories," +
                "nf_serving_size_qty,nf_serving_size_unit,nf_serving_weight_grams" +
                "&appId=3f320916&appKey=fc58ccfd02cc5e1d32acce42ecee8bf6";

        // request search
        SearchRequest x = new SearchRequest(this);
        x.searchFoods(this, url);
    }

    @Override
    public void gotFoods(ArrayList<FoodItem> foods) {
        // save ArrayList of foods that were found
        foodsFound = foods;
        ArrayList<String> foodNames = new ArrayList<String>();

        // make adapter
        SearchAdapter adapter = new SearchAdapter(this, 0, foodsFound);

        // set adapter
        lv.setAdapter(adapter);
    }

    @Override
    public void gotFoodsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
