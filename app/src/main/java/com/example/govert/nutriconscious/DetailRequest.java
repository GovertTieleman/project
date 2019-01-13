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

import java.util.ArrayList;

public class DetailRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private Callback activity;

    public interface Callback {
        void gotDetails(FoodItem foodItem);
        void gotDetailsError(String message);
    }

    public DetailRequest(Context context) {
        this.context = context;
    }

    public void getDetails(Callback activity, String url) {
        // set activity
        this.activity = activity;

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
        // get JSONArray
        try {
            // get the JSONArray with food items
            JSONObject foodJSON = response.getJSONArray("foods").getJSONObject(0)
                    .getJSONObject("food");

            JSONArray nutrientsJSON = foodJSON.getJSONArray("nutrients");

            // get food name and ndbno, initialize ArrayList for nutrients
            String name = foodJSON.getJSONObject("desc").getString("name");
            String ndbno = foodJSON.getJSONObject("desc").getString("ndbno");
            ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();

            // iterate over JSONArray of nutrients, adding them to our list
            for (int i = 0; i < nutrientsJSON.length(); i++) {
                // get current Object
                JSONObject currentNutrient = nutrientsJSON.getJSONObject(i);

                nutrients.add(new Nutrient(currentNutrient.getString("name"),
                        currentNutrient.getString("unit"),
                        currentNutrient.getString("value")));
            }

            // perform Callback to activity
            activity.gotDetails(new FoodItem(name, ndbno, nutrients));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

