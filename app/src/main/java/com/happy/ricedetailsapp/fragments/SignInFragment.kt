package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.SignIn
import com.happy.ricedetailsapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    lateinit var layoutActivityDashboardBinding:FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutActivityDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        init()
        return layoutActivityDashboardBinding.root
    }

     fun init() {
         layoutActivityDashboardBinding.button.setOnClickListener(View.OnClickListener {
            val mobileNo: String =
                layoutActivityDashboardBinding.edit.getText().toString().trim({ it <= ' ' })
            if (mobileNo.isEmpty() || mobileNo.length < 10) {
                layoutActivityDashboardBinding.edit.setError("Enter a valid mobile")
                layoutActivityDashboardBinding.edit.requestFocus()
                return@OnClickListener
            }
             initFragment(mobileNo)
        })
    }
    private fun initFragment(mobileNo: String) {
        try {
            val fragmentManager = (requireActivity() as SignIn).supportFragmentManager
            val varietyPriceItemFragment = OtpFragment()
            varietyPriceItemFragment.setData(mobileNo)
            var openFragment = fragmentManager.beginTransaction()
            openFragment.replace(R.id.fragmentContainer, varietyPriceItemFragment)
                .addToBackStack(null).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}