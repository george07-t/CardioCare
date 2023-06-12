package com.example.cardiocare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GetStarted extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;
    private FirebaseAuth mAuth;
    ViewPagerAdapter viewPagerAdapter;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREFS_KEY_ONBOARDING_SHOWN = "onboardingShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(GetStarted.this, R.color.splash0));
        setContentView(R.layout.activity_get_started);
        mAuth = FirebaseAuth.getInstance();
        backbtn = findViewById(R.id.backid);
        nextbtn = findViewById(R.id.nextid);
        skipbtn = findViewById(R.id.skipid);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean onboardingShown = preferences.getBoolean(PREFS_KEY_ONBOARDING_SHOWN, false);

        if (onboardingShown) {
            // Onboarding screen has been shown before
            checkUserLoginStatus();
        } else {
            // Onboarding screen has not been shown before
            showOnboardingScreen();
        }
    }

    private void showOnboardingScreen() {
        mSLideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSLideViewPager.getCurrentItem() > 0) {
                    mSLideViewPager.setCurrentItem(mSLideViewPager.getCurrentItem() - 1, true);
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSLideViewPager.getCurrentItem() < viewPagerAdapter.getCount() - 1) {
                    mSLideViewPager.setCurrentItem(mSLideViewPager.getCurrentItem() + 1, true);
                } else {
                    saveOnboardingShownFlag();
                    checkUserLoginStatus();
                }
            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnboardingShownFlag();
                checkUserLoginStatus();
            }
        });
    }

    private void setUpIndicator(int position) {
        dots = new TextView[viewPagerAdapter.getCount()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.inactive));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.active));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if (position > 0) {
                backbtn.setVisibility(View.VISIBLE);
            } else {
                backbtn.setVisibility(View.INVISIBLE);
            }
            if (position == viewPagerAdapter.getCount() - 1) {
                nextbtn.setText("Finish");
            } else {
                nextbtn.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void saveOnboardingShownFlag() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFS_KEY_ONBOARDING_SHOWN, true);
        editor.apply();
    }

    private void checkUserLoginStatus() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            // User is logged in
            Intent intent = new Intent(GetStarted.this, MainActivity.class);
            startActivity(intent);
        } else {
            // User is not logged in
            Intent intent = new Intent(GetStarted.this, UserLogin.class);
            startActivity(intent);
        }
        finish();
    }
}
