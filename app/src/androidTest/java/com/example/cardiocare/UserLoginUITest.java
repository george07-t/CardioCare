package com.example.cardiocare;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class UserLoginUITest {
    @Rule
    public ActivityScenarioRule<UserLogin> activityScenarioRule=
            new ActivityScenarioRule<>(UserLogin.class);

    @Test
    public void testUserLogin(){
        onView(withId(R.id.login_page)).check(matches(isDisplayed()));
        onView(withId(R.id.loginEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.forgotpassid)).check(matches(isDisplayed()));
        onView(withId(R.id.signupRedirectText)).check(matches(isDisplayed()));

        String email = "masudurrabby8@gmail.com";
        String password = "masudur1234";

        onView(withId(R.id.loginEmail)).perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));


    }

}