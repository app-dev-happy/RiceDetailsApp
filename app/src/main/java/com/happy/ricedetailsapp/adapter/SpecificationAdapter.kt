package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.databinding.ClearanceItemLayoutBinding
import com.happy.ricedetailsapp.pojo.SeaPortContent
import com.happy.ricedetailsapp.pojo.Specification

class SpecificationAdapter(val mContext: Context, val specificationList: ArrayList<Specification>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding: ClearanceItemLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = ClearanceItemLayoutBinding.inflate(inflter, parent, false)
        return SpecificationAdapterViewHolder(mContext, binding)
    }

    override fun getItemCount(): Int {
        return specificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = specificationList.get(position)
        (holder as SpecificationAdapterViewHolder).mBinding.clearancePort.text = item.title
        (holder as SpecificationAdapterViewHolder).mBinding.clearancePortValue.text = item.value

    }

    inner class SpecificationAdapterViewHolder(
        private val mContext: Context,
        val mBinding: ClearanceItemLayoutBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)
}