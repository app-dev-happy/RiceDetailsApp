package com.happy.ricedetailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.happy.ricedetailsapp.databinding.LayoutActivityDashboardBinding
import com.happy.ricedetailsapp.fragments.DashboardFragment
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.viewModel.DashboardViewModel
import org.json.JSONObject


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
            fragmentManager?.beginTransaction()?.setCustomAnimations(R.anim.slide_up,R.anim.fragment_close_exit)?.replace(R.id.fragmentContainer, DashboardFragment())?.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onBackPressed() {

        try {
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
