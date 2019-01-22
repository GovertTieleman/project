package com.example.govert.nutriconscious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SearchAdapter extends ArrayAdapter<FoodItemSimple> {
    private ArrayList<FoodItemSimple> foodItems;

    public SearchAdapter(Context context, int resource, ArrayList<FoodItemSimple> results) {
        super(context, resource, results);
        foodItems = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        // inflate list
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.search_row, parent,
                    false);
        }

        // get current FoodItemSimple
        FoodItemSimple currentFoodItem = foodItems.get(position);

        // set name, amount and unit for Nutrient
        TextView itemName = (TextView) listItem.findViewById(R.id.textViewItemName);
        itemName.setText(currentFoodItem.getName());

        TextView serving = (TextView) listItem.findViewById(R.id.textViewServing);
        serving.setText(String.format(Locale.getDefault(), "%.2f %s",
                currentFoodItem.getServingQTY(), currentFoodItem.getServingSize()));

        TextView KCal = (TextView) listItem.findViewById(R.id.textViewKCal);
        KCal.setText(String.format(Locale.getDefault(),
                "%.0f KCal", currentFoodItem.getCalories()));

        return listItem;
    }
}
