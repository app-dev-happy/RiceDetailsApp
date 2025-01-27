package com.happy.ricedetailsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.utility.DashboardRepository
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class SplashScreenActivity : AppCompatActivity(){
    lateinit var mDashboardViewModel: DashboardViewModel
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splash_screen)
        mDashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        if(DashboardRepository.isNetworkAvailable(this.applicationContext))
            mDashboardViewModel.readDashboardFile(this)
      /*  val mRef = FirebaseDatabase.getInstance().reference
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val jsonString: String = Gson().toJson(snapshot.getValue())
                if (jsonString.length>0) {
                    mDashboardViewModel.dataString.value = jsonString
                }
            }})*/
        val window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.screen_bg))
        if(DashboardRepository.getString(this.applicationContext,"first_launch","").isEmpty()){
            Handler().postDelayed({
                val intent = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                finish()
            },4000)
        }
        else {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                finish()
            }, SPLASH_TIME_OUT.toLong())
        }
    }

    //all hideKeyboard methods
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        var SPLASH_TIME_OUT = 2500
    }

}
