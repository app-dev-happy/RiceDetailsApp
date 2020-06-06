package com.happy.ricedetailsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.databinding.PackagingRecyclerItemBinding
import com.happy.ricedetailsapp.databinding.RateRecyclerItemBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.KgsWeightItem
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class RateCardsItemAdapter(val mContext: CategoryDetaillsFragment):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding:RateRecyclerItemBinding
    var ratesList:ArrayList<KgsWeightItem> = ArrayList<KgsWeightItem>()
    val viewModel = ViewModelProviders.of(mContext.requireActivity()).get(DashboardViewModel::class.java)
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
        val currencyFactor = viewModel.currencyRates.value!!.get(viewModel.selectedCurrencyKey.value)
        val number = (rateItem.price.toDouble())*currencyFactor!!
        val number3digits:Double = Math.round(number * 1000.0) / 1000.0
        val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
        (holder as RateCardsItemAdapterViewHolder).mBinding.rate.text = number2digits.toString()+viewModel.selectedCurrencySymbol.value
    }

    fun setData(ratesList: ArrayList<KgsWeightItem>) {
        this.ratesList = ratesList
        notifyDataSetChanged()
    }

    inner class RateCardsItemAdapterViewHolder(private val mContext: CategoryDetaillsFragment, val mBinding: RateRecyclerItemBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)
}