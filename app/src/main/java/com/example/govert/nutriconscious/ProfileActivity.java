package com.example.govert.nutriconscious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void updateClicked(View view) {
    }

    public void diaryClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, DiaryActivity.class));
    }

    public void homeClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}
