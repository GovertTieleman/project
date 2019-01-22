package com.example.govert.nutriconscious;

import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FoodItem implements Serializable {

    // name and ID of FoodItem
    private String name;
    private int id;
    private String date;

    // nutritional properties per serving
    private Float calories;
    private Float protein;
    private Float carbohydrate;
    private Float fat;

    // number of servings, etc
    private Float servingQTY;
    private String servingSize;
    private Float servingWeight;

    public FoodItem(String name, int id, String date, Float calories, Float protein, Float carbohydrate, Float fat, Float servingQTY, String servingSize, Float servingWeight) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.servingQTY = servingQTY;
        this.servingSize = servingSize;
        this.servingWeight = servingWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getServingQTY() {
        return servingQTY;
    }

    public void setServingQTY(Float servingQTY) {
        this.servingQTY = servingQTY;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public Float getServingWeight() {
        return servingWeight;
    }

    public void setServingWeight(Float servingWeight) {
        this.servingWeight = servingWeight;
    }

    public static String makeDate() {
        // create DateFormat
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        // get Date
        Calendar calendar = Calendar.getInstance();
        String date = df.format(Calendar.getInstance(Locale.getDefault()).getTime());

        return date;
    }

    public static ArrayList<FoodItem> getFoodsFromCursor(Cursor cursor) {
        // initialize ArrayList
        ArrayList<FoodItem> foodItems = new ArrayList<>();

        // check if a food exists
        try {
            while (cursor.moveToNext()) {

                // get all the properties
                String name = cursor.getString(cursor.getColumnIndex("item_name"));

                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                String date = cursor.getString(cursor.getColumnIndex("date"));

                Float calories = cursor.getFloat(cursor.getColumnIndex("calories"));

                Float protein = cursor.getFloat(cursor.getColumnIndex("protein"));

                Float carbohydrate = cursor.getFloat(cursor.getColumnIndex(
                        "carbohydrate"));
                Float fat = cursor.getFloat(cursor.getColumnIndex("fat"));

                Float servingQTY = cursor.getFloat(cursor.getColumnIndex(
                        "serving_quantity"));

                String servingSize = cursor.getString(cursor.getColumnIndex(
                        "serving_size"));

                Float servingWeight = cursor.getFloat(cursor.getColumnIndex(
                        "serving_weight"));

                // add new FoodItem to ArrayList
                foodItems.add(new FoodItem(name, id, date, calories, protein, carbohydrate, fat,
                        servingQTY, servingSize, servingWeight));
            }
        } finally {
            // close cursor
            cursor.close();
        }

        // return false if no user exists
        return foodItems;
    }
}
