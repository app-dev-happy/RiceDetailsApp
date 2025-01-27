package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.LayoutRiceMainVarietyBinding
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.DashboardMainContent
import com.squareup.picasso.Picasso

class DashboardMainRecyclerAdapter(
    val mContext: Context?,
    val dashboardMainContent: ArrayList<DashboardMainContent>,
    val dashBoardMainPojo: DashBoardMainPojo
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var mAdapter: RiceCatagoryItemAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding: LayoutRiceMainVarietyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_rice_main_variety,
            parent,
            false
        )
        return DashboardMainRecyclerAdapterViewHolder(mContext, binding)
    }

    override fun getItemCount(): Int {
        return dashboardMainContent.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dashboardMainContent.get(position)
        (holder as DashboardMainRecyclerAdapterViewHolder).mBinding.riceItemText.text = item.title
        if (item.iconURL.isNotEmpty()) {
            Picasso.get().load(item.iconURL)
                .into((holder as DashboardMainRecyclerAdapterViewHolder).mBinding.varietyImg)
        } else {
            Picasso.get()
                .load(R.drawable.rice_grain_fade_logo)
                .into((holder as DashboardMainRecyclerAdapterViewHolder).mBinding.varietyImg);
        }
        if(item.subTitle.isNotEmpty()){
            (holder as DashboardMainRecyclerAdapterViewHolder).mBinding.subtitle.text = item.subTitle
        }
        mAdapter = RiceCatagoryItemAdapter(mContext, item.varietyItems, dashBoardMainPojo)
        holder.mBinding.recycler.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        holder.mBinding.recycler.adapter = mAdapter
    }

    inner class DashboardMainRecyclerAdapterViewHolder(
        private val mContext: Context?,
        val mBinding: LayoutRiceMainVarietyBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)

}
