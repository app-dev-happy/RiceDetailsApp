package com.happy.ricedetailsapp.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.PackagingRecyclerItemBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.Packing
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class PackagingItemAdapter(val mContext: CategoryDetaillsFragment,val packagingList:ArrayList<Packing>,var checkPosition:Int,val context:Context):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
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
        (holder as PackagingItemAdapterViewHolder).boo(packagingItem)
    }

    inner class PackagingItemAdapterViewHolder(private val mContext: CategoryDetaillsFragment, val mBinding: PackagingRecyclerItemBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root){
        fun boo(packagingItem:Packing){
            if (checkPosition == -1) {
                mBinding.typeName.background = context!!.resources.getDrawable(R.drawable.white_rounded_bg)
                mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.black))
            } else {
                if (checkPosition == adapterPosition) {
                  mBinding.typeName.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
                 mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.white))
                } else {
                   mBinding.typeName.background = context!!.resources.getDrawable(R.drawable.white_rounded_bg)
                  mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.black))
                }
            }
            mBinding.typeName.text = packagingItem.title
            mBinding.root.setOnClickListener(View.OnClickListener {
                mBinding.typeName.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
                mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.white))
                if (checkPosition != adapterPosition) {
                    notifyItemChanged(checkPosition!!)
                    checkPosition = adapterPosition
                    viewModel.packagingPosition.value = position
                    mContext.setPackingPosition(checkPosition)
                }
            }
            )
        }
    }

    }