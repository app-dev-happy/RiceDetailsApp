package com.happy.ricedetailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.happy.ricedetailsapp.databinding.LayoutActivityDashboardBinding
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.viewModel.DashboardViewModel


class DashboardActivity : AppCompatActivity() {

    lateinit var layoutActivityDashboardBinding: LayoutActivityDashboardBinding
    var fragmentManager: FragmentManager? = null
    var doubleBackToExitOnce:Boolean = false
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

    override fun onBackPressed() {

        try {
            AppConstant.backPressed = true
            if (fragmentManager!!.fragments != null && fragmentManager!!.fragments.size > 0 &&
                !(fragmentManager!!.fragments.get(0) is DashboardFragment ))
            {
                super.onBackPressed()
                return
            }

            if(doubleBackToExitOnce){
                super.onBackPressed()
                return
            }

            this.doubleBackToExitOnce = true

            //displays a toast message when user clicks exit button
            Toast.makeText(this,"please press again to exit", Toast.LENGTH_LONG ).show()

            //displays the toast message for a while
            Handler().postDelayed({
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
