package com.example.govert.nutriconscious;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DetailRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private Callback activity;
    private FoodItemSimple selectedFood;

    public interface Callback {
        void gotDetails(FoodItem foodItem);
        void gotDetailsError(String message);
    }

    public DetailRequest(Context context) {
        this.context = context;
    }

    public void getDetails(Callback activity, String url, FoodItemSimple foodItemSimple) {
        // set activity
        this.activity = activity;

        // get selectedFood
        selectedFood = foodItemSimple;

        // create RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // create JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotDetailsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        // get nutrient info
        Float protein;
        try {
            protein = BigDecimal.valueOf(response.getDouble("nf_protein")).floatValue();
        }
        catch (JSONException e) {
            protein = 0f;
        }

        Float carbohydrate;
        try {
            carbohydrate = BigDecimal.valueOf(response.getDouble(
                    "nf_total_carbohydrate")).floatValue();
        }
        catch (JSONException e) {
            carbohydrate = 0f;
        }

        Float fat;
        try {
            fat = BigDecimal.valueOf(response.getDouble("nf_total_fat")).floatValue();
        }
        catch (JSONException e) {
            fat = 0f;
        }

        Float servingWeightGrams;
        try {
            servingWeightGrams = BigDecimal.valueOf(response.getDouble(
                    "nf_serving_weight_grams")).floatValue();
        }
        catch (JSONException e) {
            servingWeightGrams = 0f;
        }

        // calculate nutrients per serving
        Float servings = selectedFood.getServingQTY();

        Float calories = (selectedFood.getCalories() / servings);
        protein = (protein / servings);
        carbohydrate = (carbohydrate / servings);
        fat = (fat / servings);

        // create FoodItem
        FoodItem foodItem = new FoodItem(selectedFood.getName(), 0, calories,
                protein, carbohydrate, fat, selectedFood.getServingQTY(),
                selectedFood.getServingSize(), servingWeightGrams);

        // perform Callback to activity
        activity.gotDetails(foodItem);
    }
}

