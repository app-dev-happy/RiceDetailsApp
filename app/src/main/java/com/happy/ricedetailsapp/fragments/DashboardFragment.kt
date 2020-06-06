package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.happy.ricedetailsapp.R
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.databinding.LayoutFragmentDashboardBinding
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.DashboardMainContent
import com.happy.ricedetailsapp.pojo.SeaPortContent
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class DashboardFragment : Fragment(), View.OnClickListener {

    lateinit var layoutFragmentDashboardBinding: LayoutFragmentDashboardBinding
    internal lateinit var view : View
    lateinit var mDashboardViewModel: DashboardViewModel
    lateinit var adapter:DashboardMainRecyclerAdapter

    override fun onClick(view: View?) {   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutFragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_dashboard, container, false)
        layoutFragmentDashboardBinding.executePendingBindings()
        view =  layoutFragmentDashboardBinding.root
        mDashboardViewModel = ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        getFileData()
        init()
        return view
    }

    private fun init() {
        openScreen()
        initViews()
        initListener()
    }

    fun getFileData(){
        mDashboardViewModel.readDashboardFile(context!!).observe(requireActivity() as LifecycleOwner,
            Observer {
                if(it!=null&&it.length>0){
                    val dashBoardMainPojo = Gson().fromJson(it,DashBoardMainPojo::class.java)
                    val CurrencyContent = dashBoardMainPojo.CurrencyContent
                    val dashboardMainContent = dashBoardMainPojo.DashboardMainContent
                    val seaPortContent = dashBoardMainPojo.SeaPortContent
                    setAdapter(dashboardMainContent)
                }
            })
    }

    private fun setAdapter(dashboardMainContent:ArrayList<DashboardMainContent>) {
        adapter = DashboardMainRecyclerAdapter(context,dashboardMainContent)
        layoutFragmentDashboardBinding.mainRecycler.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutFragmentDashboardBinding.mainRecycler.adapter = adapter
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