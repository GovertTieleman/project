package com.example.govert.nutriconscious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter<String> {
    private ArrayList<String> userInfo;

    public ProfileAdapter(Context context, int resource, ArrayList<String> information) {
        super(context, resource, information);
        userInfo = information;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        // inflate list
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.profile_row, parent,
                    false);
        }

        // get currentNutrient
        String currentItem = userInfo.get(position);

        // set name, amount and unit for Nutrient
        TextView field = (TextView) listItem.findViewById(R.id.field);
        field.setText(currentItem.split("_")[0]);

        TextView value = (TextView) listItem.findViewById(R.id.value);
        value.setText(currentItem.split("_")[1]);

        return listItem;
    }
}
