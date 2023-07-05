package com.example.cardiocare;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest

public class MainActivityAddUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule=
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddData(){


        onView(withId(R.id.adddataid)).perform(click());
        onView(withId(R.id.sysid)).perform(ViewActions.typeText("123"));
        onView(withId(R.id.dyaid)).perform(ViewActions.typeText("70"));
        onView(withId(R.id.heartid)).perform(ViewActions.typeText("75"));

        Espresso.pressBack();

        onView(withId(R.id.datainsertid)).perform(click());


    }

    @Test
    public  void testUpdateData(){
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.cardview_of_data)).check(matches(isDisplayed()));
        onView(withId(R.id.editdataid)).check(matches(isDisplayed()));

        onView(withId(R.id.editdataid)).perform(click());

        onView(withId(R.id.update_data_page)).check(matches(isDisplayed()));
        onView(withId(R.id.syseditid)).check(matches(isDisplayed()));
        onView(withId(R.id.dyaeditid)).check(matches(isDisplayed()));
        onView(withId(R.id.hearteditid)).check(matches(isDisplayed()));
        onView(withId(R.id.dataeditid)).check(matches(isDisplayed()));

        onView(withId(R.id.syseditid)).perform(typeText("134"));
        onView(withId(R.id.dyaeditid)).perform(typeText("80"));
        onView(withId(R.id.hearteditid)).perform(typeText("65"));

        Espresso.pressBack();

        onView(withId(R.id.dataeditid)).perform(click());

    }


    @Test
    public void testDeleteData(){

        onView(withId(R.id.deletedaytaid)).perform(click());
    }

}