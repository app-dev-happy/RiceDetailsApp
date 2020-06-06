package com.happy.ricedetailsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.databinding.PackagingRecyclerItemBinding
import com.happy.ricedetailsapp.databinding.RateRecyclerItemBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.KgsWeightItem

class RateCardsItemAdapter(val mContext: CategoryDetaillsFragment, val ratesList:ArrayList<KgsWeightItem>):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding:RateRecyclerItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = RateRecyclerItemBinding.inflate(inflter,parent,false)
        return RateCardsItemAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      val rateItem = ratesList.get(position)
        (holder as RateCardsItemAdapterViewHolder).mBinding.weight.text = rateItem.weight
        (holder as RateCardsItemAdapterViewHolder).mBinding.rate.text = rateItem.price
    }

    inner class RateCardsItemAdapterViewHolder(private val mContext: CategoryDetaillsFragment, val mBinding: RateRecyclerItemBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)
}