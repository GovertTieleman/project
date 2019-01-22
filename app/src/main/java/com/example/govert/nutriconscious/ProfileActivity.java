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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }

    public void diaryClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, DiaryActivity.class));
        finish();

    }

    public void homeClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }

    public void profileClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        finish();
    }
}
