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

/**
 * This activity displays the onboarding screen to the user.
 * If the onboarding screen has been shown before, it checks the user's login status.
 * If the onboarding screen has not been shown before, it shows the onboarding screen to the user.
 */
public class GetStarted extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn, getstart;

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
        getstart = findViewById(R.id.getstart);

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

    /**
     * Shows the onboarding screen to the user.
     * Sets up the ViewPager, indicators, and click listeners for buttons.
     */
    private void showOnboardingScreen() {
        mSLideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
        getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSLideViewPager.getCurrentItem() == viewPagerAdapter.getCount() - 1) {
                    saveOnboardingShownFlag();
                    checkUserLoginStatus();
                }

            }
        });
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

    /**
     * Sets up the indicators for the onboarding slides.
     *
     * @param position The current position of the slide
     */
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

    /**
     * Listener for ViewPager page changes.
     * Updates the indicators and visibility of buttons based on the current page position.
     */
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
                nextbtn.setVisibility(View.INVISIBLE);
                skipbtn.setVisibility(View.INVISIBLE);
                getstart.setVisibility(View.VISIBLE);
            } else {
                nextbtn.setText("Next");
                getstart.setVisibility(View.INVISIBLE);
                skipbtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * Saves the flag indicating that the onboarding screen has been shown.
     */
    private void saveOnboardingShownFlag() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFS_KEY_ONBOARDING_SHOWN, true);
        editor.apply();
    }

    /**
     * Checks the user's login status and navigates to the appropriate activity.
     * If the user is logged in, it navigates to the MainActivity.
     * If the user is not logged in, it navigates to the UserLogin activity.
     */
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
