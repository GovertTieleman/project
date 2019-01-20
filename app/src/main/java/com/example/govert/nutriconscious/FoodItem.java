package com.example.govert.nutriconscious;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodItem implements Serializable {

    // name and ID of FoodItem
    private String name;
    private int id;

    // nutritional properties per serving
    private Float calories;
    private Float protein;
    private Float carbohydrate;
    private Float fat;

    // number of servings, etc
    private Float servingQTY;
    private String servingSize;
    private Float servingWeight;

    public FoodItem(String name, int id, Float calories, Float protein, Float carbohydrate, Float fat, Float servingQTY, String servingSize, Float servingWeight) {
        this.name = name;
        this.id = id;
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
}
