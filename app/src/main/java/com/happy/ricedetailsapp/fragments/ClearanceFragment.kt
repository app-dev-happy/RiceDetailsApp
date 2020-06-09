package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.ClearanceAdapter
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.databinding.LayoutClearanceBinding
import com.happy.ricedetailsapp.pojo.SeaPortContent

class ClearanceFragment : Fragment() {
    lateinit var mBinding: LayoutClearanceBinding
    var clearancePortList =  ArrayList<SeaPortContent>()
    lateinit var adapter:ClearanceAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_clearance, container, false)
        mBinding.executePendingBindings()
        init()
        return  mBinding.root
    }

    fun init(){
        setAdapter()
        initListner()
    }

    private fun initListner() {
        mBinding.backIcon.setOnClickListener {
            (context as DashboardActivity).onBackPressed()
        }
    }

    private fun setAdapter() {
        adapter = ClearanceAdapter(context!!,clearancePortList)
        mBinding.clearanceRecycler.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.clearanceRecycler.adapter = adapter
    }

    fun setData(clearancePortList: ArrayList<SeaPortContent>) {
        this.clearancePortList  = clearancePortList
    }
}