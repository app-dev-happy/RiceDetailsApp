package com.happy.ricedetailsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.happy.ricedetailsapp.databinding.LayoutActivityDashboardBinding
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.fragments.SignInFragment
import com.happy.ricedetailsapp.utility.DashboardRepository
import com.happy.ricedetailsapp.viewModel.DashboardViewModel


class SignIn : AppCompatActivity(){
    lateinit var layoutActivityDashboardBinding: LayoutActivityDashboardBinding
    lateinit var mDashboardViewModel: DashboardViewModel
    var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        layoutActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.layout_activity_dashboard)
        mDashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
//        if(DashboardRepository.isNetworkAvailable(this.applicationContext))
//            mDashboardViewModel.readDashboardFile(this)
        initFragment()
//        Handler().postDelayed({
//            val intent = Intent(this@SignIn, DashboardActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
//            finish()
//        },SPLASH_TIME_OUT.toLong())
    }

    companion object {
        var SPLASH_TIME_OUT = 2500
    }
    private fun initFragment() {
        try {
            fragmentManager = supportFragmentManager
            fragmentManager?.beginTransaction()?.setCustomAnimations(R.anim.slide_up,R.anim.fragment_close_exit)?.replace(R.id.fragmentContainer, SignInFragment())?.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}