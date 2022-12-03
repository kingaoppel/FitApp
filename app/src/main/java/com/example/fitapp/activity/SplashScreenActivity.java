package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.fitapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent toLoginActivity = new Intent(SplashScreenActivity.this, LoginActivity.class);
                finish();
                startActivity(toLoginActivity);
            }
        }, 2600);

    }
}