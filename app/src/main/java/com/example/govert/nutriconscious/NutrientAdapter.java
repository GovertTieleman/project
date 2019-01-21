package com.example.govert.nutriconscious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class NutrientAdapter extends ArrayAdapter<Nutrient> {
    private ArrayList<Nutrient> nutrientList;

    public NutrientAdapter(Context context, int resource, ArrayList<Nutrient> nutrients) {
        super(context, resource, nutrients);
        nutrientList = nutrients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        // inflate list
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.nutrient_row, parent,
                    false);
        }

        // get currentNutrient
        Nutrient currentNutrient = nutrientList.get(position);

        // set name, amount and unit for Nutrient
        TextView name = (TextView) listItem.findViewById(R.id.textViewNutrientName);
        name.setText(currentNutrient.getName());

        TextView value = (TextView) listItem.findViewById(R.id.textViewNutrientValue);
        value.setText(String.format(Locale.getDefault(),"%.2f", currentNutrient.getValue()));

        TextView unit = (TextView) listItem.findViewById(R.id.textViewNutrientUnit);
        unit.setText(currentNutrient.getUnit());

        return listItem;
    }
}
