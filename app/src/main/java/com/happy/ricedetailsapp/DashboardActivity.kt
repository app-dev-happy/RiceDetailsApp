package com.happy.ricedetailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.happy.ricedetailsapp.databinding.LayoutActivityDashboardBinding
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.viewModel.DashboardViewModel


class DashboardActivity : AppCompatActivity() {

    lateinit var layoutActivityDashboardBinding: LayoutActivityDashboardBinding
    var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.layout_activity_dashboard)
        initFragment()
    }

    private fun initFragment() {
        try {
            fragmentManager = supportFragmentManager
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, DashboardFragment())?.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
