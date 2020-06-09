package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.databinding.ClearanceItemLayoutBinding
import com.happy.ricedetailsapp.pojo.SeaPortContent

class ClearanceAdapter(val mContext: Context,val clearancePortList: ArrayList<SeaPortContent>):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding:ClearanceItemLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = ClearanceItemLayoutBinding.inflate(inflter,parent,false)
        return ClearanceAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
       return clearancePortList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item  = clearancePortList.get(position)
        (holder as ClearanceAdapterViewHolder).mBinding.clearancePort.text = item.title
    }
    inner class ClearanceAdapterViewHolder(private val mContext: Context, val mBinding: ClearanceItemLayoutBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)
}