package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.LayoutFragmentDashboardBinding

class DashboardFragment : Fragment(), View.OnClickListener {

    lateinit var layoutFragmentDashboardBinding: LayoutFragmentDashboardBinding
    internal lateinit var view : View

    override fun onClick(view: View?) {   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutFragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_dashboard, container, false)
        layoutFragmentDashboardBinding.executePendingBindings()
        view =  layoutFragmentDashboardBinding.root
        init()
        return view
    }

    private fun init() {
        openScreen()
        initViews()
        initListener()
    }

    private fun openScreen() {
        try {
            layoutFragmentDashboardBinding.root.animation =
                AnimationUtils.loadAnimation(context, R.anim.slide_up)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initListener() {

    }

    private fun initViews() {

    }


}