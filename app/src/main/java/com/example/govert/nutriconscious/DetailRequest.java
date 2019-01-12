package com.example.govert.nutriconscious;

import android.content.Context;

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
            // initialize ArrayList
            FoodItem foodItem = new FoodItem;

            // get the JSONArray with food items
            JSONArray items = response.getJSONObject("list").getJSONArray("item");

            // iterate over items, adding their db number to the ArrayList
            for (int i = 0; i < items.length(); i++) {
                // get current Object
                JSONObject currentItem = items.getJSONObject(i);

                // add db number
                foodList.add(new FoodItem(currentItem.getString("name"), currentItem.getInt("ndbno")));
            }

            // perform Callback to activity
            activity.gotDetails(foodItem);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

