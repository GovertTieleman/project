package com.example.govert.nutriconscious;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodItem implements Serializable {

    private String name;
    private String ndbno;
    private ArrayList<Nutrient> nutrients;

    public FoodItem(String name, String ndbno, ArrayList<Nutrient> nutrients) {
        this.name = name;
        this.ndbno = ndbno;
        this.nutrients = nutrients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }
}
