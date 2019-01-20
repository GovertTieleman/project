package com.example.govert.nutriconscious;

import java.io.Serializable;

public class FoodItemSimple implements Serializable {

    private String name;
    private String idAPI;

    private Float calories;

    private Float servingQTY;
    private String servingSize;

    public FoodItemSimple(String name, String idAPI, Float calories, Float servingQTY, String servingSize) {
        this.name = name;
        this.idAPI = idAPI;
        this.calories = calories;
        this.servingQTY = servingQTY;
        this.servingSize = servingSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdAPI() {
        return idAPI;
    }

    public void setIdAPI(String id) {
        this.idAPI = id;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
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
}
