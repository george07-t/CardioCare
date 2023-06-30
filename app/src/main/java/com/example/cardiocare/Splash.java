package com.example.cardiocare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Splash screen activity that is displayed when the app is launched.
 * Shows a splash screen for a certain duration and then redirects the user to the appropriate activity.
 */
public class Splash extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.splash0));
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Splash.this, GetStarted.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}