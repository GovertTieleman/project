package com.example.govert.nutriconscious;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodItem implements Serializable {

    private String name;
    private String id;
    private double calories;
    private ArrayList<Nutrient> nutrients;
    private int servingQTY;
    private String servingSize;
    private double servingWeight;

    public FoodItem(String name, String id, double calories, ArrayList<Nutrient> nutrients, int servingQTY, String servingSize, double servingWeight) {
        this.name = name;
        this.id = id;
        this.calories = calories;
        this.nutrients = nutrients;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public int getServingQTY() {
        return servingQTY;
    }

    public void setServingQTY(int servingQTY) {
        this.servingQTY = servingQTY;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public double getServingWeight() {
        return servingWeight;
    }

    public void setServingWeight(double servingWeight) {
        this.servingWeight = servingWeight;
    }
}
