package com.example.govert.nutriconscious;

import java.io.Serializable;

public class FoodItem implements Serializable {

    private String name;
    private int ndbno;
    private double KCal;
    private double protein;
    private double carb;
    private double fat;

    public FoodItem(String name, int ndbno, double KCal, double protein, double carb, double fat) {
        this.name = name;
        this.ndbno = ndbno;
        this.KCal = KCal;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNdbno() {
        return ndbno;
    }

    public void setNdbno(int ndbno) {
        this.ndbno = ndbno;
    }

    public double getKCal() {
        return KCal;
    }

    public void setKCal(double KCal) {
        this.KCal = KCal;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }
}
