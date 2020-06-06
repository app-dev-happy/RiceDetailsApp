package com.happy.ricedetailsapp.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.PackagingRecyclerItemBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.Packing
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class PackagingItemAdapter(val mContext: CategoryDetaillsFragment,val packagingList:ArrayList<Packing>,var selectedPosition:Int,val context:Context):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding:PackagingRecyclerItemBinding
    val viewModel = ViewModelProviders.of(mContext).get(DashboardViewModel::class.java)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = PackagingRecyclerItemBinding.inflate(inflter,parent,false)
        return PackagingItemAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
        return packagingList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val packagingItem = packagingList.get(position)
        if(selectedPosition==position){
            (holder as PackagingItemAdapterViewHolder).mBinding.typeName.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
            (holder as PackagingItemAdapterViewHolder).mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.white))
        }
        (holder as PackagingItemAdapterViewHolder).mBinding.typeName.text = packagingItem.title
        (holder as PackagingItemAdapterViewHolder).mBinding.root.setOnClickListener {
            viewModel.packagingPosition.value = position
            notifyItemChanged(selectedPosition)
            selectedPosition

        }
    }

    inner class PackagingItemAdapterViewHolder(private val mContext: CategoryDetaillsFragment, val mBinding: PackagingRecyclerItemBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)

    }