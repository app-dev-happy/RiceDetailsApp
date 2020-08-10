package com.happy.ricedetailsapp.fragment

import android.content.Context
import android.content.Intent
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
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.utility.DashboardRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.json.JSONObject
import org.junit.*
import java.util.*


class  DashboardFragmentTest {
    @get:Rule
    var rule: ActivityTestRule<DashboardActivity> = object : ActivityTestRule<DashboardActivity>(
        DashboardActivity::class.java
    ) {
        override fun getActivityIntent(): Intent {
            InstrumentationRegistry.getInstrumentation().targetContext
            val intent = Intent(Intent.ACTION_MAIN)
            intent.putExtra("MYKEY", "Hello")
            return intent
        }
    }
    var mActivity:Context?=null
    lateinit var fragment:DashboardFragment
    @Before
    fun setUp(){
        mActivity = rule.activity
        fragment = DashboardFragment()
        startFragment()
    }

    private fun startFragment() {
        rule.activity.fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer,fragment )?.commit()
        rule.activity.runOnUiThread(Runnable {
            rule.activity.getSupportFragmentManager().executePendingTransactions()
        })
        getInstrumentation().waitForIdleSync();
    }

    @Test
    fun testFirst(){
        val viewId:TextView = fragment.layoutFragmentDashboardBinding.appName
        Assert.assertEquals(viewId.getText().toString(),"DetailsApp")
    }
    @Test
    fun testSecond(){
        val viewId:TextView = fragment.layoutFragmentDashboardBinding.textProducts
        Assert.assertEquals(viewId.getText().toString(),"Products")
    }
    @Test
    fun testThird(){
        val activity = rule.activity
        val viewId:RecyclerView = activity.findViewById(R.id.main_recycler)
        val adapter = viewId.adapter
        assertThat(adapter,notNullValue());
    }
    @Test
    fun testFourth(){
        val viewModel= fragment.mDashboardViewModel
        assertThat(viewModel.currencyRates.value,notNullValue());
        Assert.assertEquals(viewModel.currencyRates.value!!.get("USD"),0.013239009)
    }
    @Test
    fun testFifth(){
        val data =  rule.activity.resources.assets.open("AndroidDashboardFile.txt").bufferedReader().use {
            it.readText()
        }
        val dashBoardMainPojo = Gson().fromJson(data, DashBoardMainPojo::class.java)
    }
    @Test
    fun testSixth(){
        fragment.layoutFragmentDashboardBinding.clearanceFab.setOnClickListener {
            startFragment()
        }
        onView(withId(fragment.layoutFragmentDashboardBinding.clearanceFab.id)).perform(click())
    }

    @After
    fun tearDown(){
        mActivity = null
    }
}