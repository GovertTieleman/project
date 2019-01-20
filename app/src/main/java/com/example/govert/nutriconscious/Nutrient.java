package com.example.govert.nutriconscious;

import java.io.Serializable;

public class Nutrient implements Serializable {

    private String name;
    private String unit;
    private Float value;

    public Nutrient(String name, String unit, Float value) {
        this.name = name;
        this.unit = unit;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
