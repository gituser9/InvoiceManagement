package com.user.invoicemanagement

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.user.invoicemanagement.view.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    /*@Test
    fun drawer_canBeOpen() {
        onView(withId(R.id.drawerLayout)).perform(click())
    }*/
}
