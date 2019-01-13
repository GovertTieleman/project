package com.example.govert.nutriconscious;

import java.io.Serializable;

public class Nutrient implements Serializable {

    private String name;
    private String unit;
    private String value;       // per 100g

    public Nutrient(String name, String unit, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
