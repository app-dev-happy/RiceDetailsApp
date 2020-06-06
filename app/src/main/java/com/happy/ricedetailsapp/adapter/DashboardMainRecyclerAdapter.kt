package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.LayoutRiceMainVarietyBinding
import com.happy.ricedetailsapp.pojo.DashboardMainContent

class DashboardMainRecyclerAdapter(val mContext: Context?,val dashboardMainContent:ArrayList<DashboardMainContent>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding:LayoutRiceMainVarietyBinding=  DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_rice_main_variety,parent,false)
        return DashboardMainRecyclerAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
      return dashboardMainContent.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val item = dashboardMainContent.get(position)
        (holder as DashboardMainRecyclerAdapterViewHolder).mBinding.languageItemText.text = item.title
    }

    inner class DashboardMainRecyclerAdapterViewHolder(private val mContext: Context?,  val mBinding: LayoutRiceMainVarietyBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)


}
