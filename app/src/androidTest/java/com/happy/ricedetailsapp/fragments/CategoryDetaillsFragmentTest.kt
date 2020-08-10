package com.happy.ricedetailsapp.fragments

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.adapter.RiceCatagoryItemAdapter
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CategoryDetaillsFragmentTest {
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
    var mActivity: Context?=null
    lateinit var fragment:DashboardFragment
    lateinit var categoryDetaillsFragment: CategoryDetaillsFragment
    lateinit var mainAdapter:DashboardMainRecyclerAdapter
    lateinit var catagoryAdapter:RiceCatagoryItemAdapter
    var dashBoardMainPojo:DashBoardMainPojo?=null
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
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        secondFragment()
    }

    @Test
    fun firstTest(){
        assertEquals(categoryDetaillsFragment.mBinding.metricTonn.text,"Metric Tonn.")
    }

    private fun secondFragment() {

        categoryDetaillsFragment = CategoryDetaillsFragment()
        categoryDetaillsFragment.setData(dashBoardMainPojo!!.dashboardMainContent.get(0).varietyItems.get(0),dashBoardMainPojo!!)
        rule.activity.fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer,categoryDetaillsFragment )?.addToBackStack(null)?.commit()
        rule.activity.runOnUiThread(Runnable {
            rule.activity.getSupportFragmentManager().executePendingTransactions()
        })
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @After
    fun tearDown() {
        mActivity = null
    }
}