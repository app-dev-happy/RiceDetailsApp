package com.happy.ricedetailsapp.adapter

import android.app.Activity
import android.content.Context
import android.util.SparseBooleanArray
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

class PackagingItemAdapter(
    val mContext: CategoryDetaillsFragment,
    val packagingList: ArrayList<Packing>,
    var checkPosition: Int,
    val context: Context
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding: PackagingRecyclerItemBinding
    var sSelectedItems = SparseBooleanArray()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = PackagingRecyclerItemBinding.inflate(inflter, parent, false)
        return PackagingItemAdapterViewHolder(mContext, binding)
    }

    override fun getItemCount(): Int {
        return packagingList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val packagingItem = packagingList.get(position)
        if (sSelectedItems.get(position) || checkPosition == position) {
            (holder as PackagingItemAdapterViewHolder).mBinding.typeName.background =
                context!!.resources.getDrawable(R.drawable.red_rounded_bg)
            holder.mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.white))
        } else {
            (holder as PackagingItemAdapterViewHolder).mBinding.typeName.background =
                context!!.resources.getDrawable(R.drawable.white_rounded_bg)
            holder.mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.black))
        }
        (holder as PackagingItemAdapterViewHolder).mBinding.typeName.text = packagingItem.title
    }

    inner class PackagingItemAdapterViewHolder(
        private val mContext: CategoryDetaillsFragment,
        val mBinding: PackagingRecyclerItemBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root),
        View.OnClickListener {
        init {
            mBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (sSelectedItems.get(getAdapterPosition(), false)) {
                sSelectedItems.delete(getAdapterPosition());
                mBinding.typeName.background =
                    context!!.resources.getDrawable(R.drawable.white_rounded_bg)
                mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.black))
            } else {
                sSelectedItems.put(checkPosition, false)
                sSelectedItems.put(getAdapterPosition(), true)
                mBinding.typeName.background =
                    context!!.resources.getDrawable(R.drawable.red_rounded_bg)
                mBinding.typeName.setTextColor(context!!.resources.getColor(R.color.white))
            }
            checkPosition = getAdapterPosition()
//            viewModel.packagingPosition.value = checkPosition
            mContext.setPackingPosition(checkPosition)
            notifyDataSetChanged()
        }
    }

}