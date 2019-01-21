package com.example.govert.nutriconscious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends ArrayAdapter<FoodItem> {
    private ArrayList<FoodItem> foodItems;

    public DiaryAdapter(Context context, int resource, ArrayList<FoodItem> results) {
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
        FoodItem currentFoodItem = foodItems.get(position);

        // set name, amount and unit for Nutrient
        TextView itemName = (TextView) listItem.findViewById(R.id.textViewItemName);
        itemName.setText(currentFoodItem.getName());

        TextView serving = (TextView) listItem.findViewById(R.id.textViewServing);
        serving.setText(String.format("%s %s", currentFoodItem.getServingQTY().toString(), currentFoodItem.getServingSize()));

        TextView KCal = (TextView) listItem.findViewById(R.id.textViewKCal);
        KCal.setText(String.format("%s KCal", currentFoodItem.getCalories().toString()));

        return listItem;
    }
}
