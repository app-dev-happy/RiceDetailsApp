package com.happy.ricedetailsapp

import android.R
import android.content.Context
import android.service.autofill.Validators.not
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class SplashScreenActivityTest{

    @get:Rule
    var rule: ActivityTestRule<SplashScreenActivity> = ActivityTestRule<SplashScreenActivity>(
        SplashScreenActivity::class.java
    )
    var mActivity:Context?=null
    @org.junit.Before
    fun setUp() {
        mActivity = rule.activity
    }
    @Test
    fun testLaunch(){
        val activity = rule.activity
        val viewId: TextView = activity.findViewById(com.happy.ricedetailsapp.R.id.app_name)
        Assert.assertEquals(viewId.getText().toString(),"DetailsApp")
    }

    @org.junit.After
    fun tearDown() {
        mActivity = null
    }
}