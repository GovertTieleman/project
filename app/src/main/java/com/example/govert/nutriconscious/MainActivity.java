package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView caloriesTextView = findViewById(R.id.textViewCalories);

        caloriesTextView.setText();
    }

    public void inputClicked(View view) {
        startActivity(new Intent(MainActivity.this, InputActivity.class));
    }

    public void planClicked(View view) {
        startActivity(new Intent(MainActivity.this, PlanActivity.class));
    }
}
