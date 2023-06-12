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
                startApp();
            }
        },3000);
/*        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {



            }

        });
        thread.start();*/
    }

    private void startApp() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            //user log in
            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(Splash.this, UserLogin.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}