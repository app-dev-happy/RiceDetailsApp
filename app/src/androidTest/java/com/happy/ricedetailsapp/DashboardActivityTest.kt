package com.happy.ricedetailsapp

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.utility.DashboardRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.json.JSONObject
import org.junit.*
import java.util.*


class DashboardActivityTest {
    @get:Rule
    var rule: ActivityTestRule<DashboardActivity> = object : ActivityTestRule<DashboardActivity>(
        DashboardActivity::class.java
    ) {
        override fun getActivityIntent(): Intent {
            InstrumentationRegistry.getInstrumentation().targetContext
            val intent = Intent(Intent.ACTION_MAIN)
            intent.putExtra("KEY", "Hello")
            return intent
        }
    }
    var mActivity:Context?=null
    @Before
    fun setUp(){
        mActivity = rule.activity
    }

    @Test
    fun testFirst(){
        val activity = rule.activity
        val viewId:View = activity.layoutActivityDashboardBinding.fragmentContainer
        assertThat(viewId,notNullValue())
    }

    @After
    fun tearDown(){
        mActivity = null
    }
}