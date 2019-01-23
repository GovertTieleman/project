package com.example.govert.nutriconscious;

import android.database.Cursor;

import org.jsoup.select.Evaluator;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String gender;
    private int height;
    private int weight;
    private int age;
    private String activity;
    private String goal;

    public User(Integer id, String gender, int height, int weight, int age, String activity, String goal) {
        this.id = id;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.activity = activity;
        this.goal = goal;
    }

    public Double getCalories() {
        Double calories;
        if (this.gender.equals("male")) {
            calories = (10 * this.weight) + (6.25 * this.height) - (5 * this.age) + 5;
        }
        else {
            calories = (10 * this.weight) + (6.25 * this.height) - (5 * this.age) - 161;
        }

        switch (activity) {
            case "sedentary":
                calories *= 1.2;
                break;
            case "normal":
                calories *= 1.3;
                break;
            case "active":
                calories *= 1.4;
                break;
        }

        switch (goal) {
            case "lose":
                calories -= 300;
                break;
            case "maintain":
                break;
            case "gain":
                calories += 300;
                break;
        }

        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public static User getUser(Cursor cursor) {
        User user;
        if (cursor.moveToFirst()) {
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            int height = cursor.getInt(cursor.getColumnIndex("height"));
            int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String activity = cursor.getString(cursor.getColumnIndex("activity"));
            String goal = cursor.getString(cursor.getColumnIndex("goal"));
            user = new User(null, gender, height, weight, age, activity, goal);

            return user;
        }

        return null;
    }
}
