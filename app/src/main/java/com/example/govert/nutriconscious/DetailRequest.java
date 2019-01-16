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
        void gotDetails(ArrayList<Nutrient> nutrients);
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
            // create ArrayList of nutrients
            ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();

            // add the relevant nutrients to the list
            response.getString("fat");

            // perform Callback to activity
            activity.gotDetails(nutrients);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

