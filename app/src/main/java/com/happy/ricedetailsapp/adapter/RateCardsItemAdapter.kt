package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.RateRecyclerItemBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.KgsWeightItem
import com.happy.ricedetailsapp.viewModel.DashboardViewModel


class RateCardsItemAdapter(val mContext: CategoryDetaillsFragment, val content: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding: RateRecyclerItemBinding
    var kgsBtnSelected: Boolean = true
    var checkPosition: Int = 0
    var sSelectedItems = SparseBooleanArray()
    var ratesList: ArrayList<KgsWeightItem> = ArrayList<KgsWeightItem>()
    val viewModel =
        ViewModelProviders.of(mContext.requireActivity()).get(DashboardViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = RateRecyclerItemBinding.inflate(inflter, parent, false)
        return RateCardsItemAdapterViewHolder(mContext, binding)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rateItem = ratesList.get(position)
        if (sSelectedItems.get(position) || checkPosition == position) {
            (holder as RateCardsItemAdapterViewHolder).mBinding.rateCard.setCardBackgroundColor(
                mContext!!.resources.getColor(R.color.red)
            )
            viewModel.rateCardPosition.value  = position
            holder.mBinding.rate.setTextColor(mContext!!.resources.getColor(R.color.white))
            holder.mBinding.divider.visibility = View.VISIBLE
            viewModel.rateCardValue.value = rateItem
        } else {
            (holder as RateCardsItemAdapterViewHolder).mBinding.rateCard.setCardBackgroundColor(
                mContext!!.resources.getColor(R.color.white)
            )
            holder.mBinding.rate.setTextColor(mContext!!.resources.getColor(R.color.red))
            holder.mBinding.divider.visibility = View.VISIBLE
        }
        if (kgsBtnSelected)
            (holder as RateCardsItemAdapterViewHolder).mBinding.weight.text =
                rateItem.weight + content!!.resources.getString(R.string.kgs_txt)
        else
            (holder as RateCardsItemAdapterViewHolder).mBinding.weight.text =
                rateItem.weight + content!!.resources.getString(R.string.lbs_txt)

        val currencyFactor =
            viewModel.currencyRates.value!!.get(viewModel.selectedCurrencyKey.value)
        val number = (rateItem.price.toDouble()) * currencyFactor!!
        val number3digits: Double = Math.round(number * 1000.0) / 1000.0
        val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
        (holder as RateCardsItemAdapterViewHolder).mBinding.rate.text =
            viewModel.selectedCurrencySymbol.value + number2digits.toString()
    }

    fun setData(ratesList: ArrayList<KgsWeightItem>, kgsBtnSelected: Boolean, checkPosition: Int) {
        this.ratesList = ratesList
        this.kgsBtnSelected = kgsBtnSelected
        this.checkPosition = checkPosition
        this.sSelectedItems.clear()
        notifyDataSetChanged()
    }

    inner class RateCardsItemAdapterViewHolder(
        private val context: CategoryDetaillsFragment,
        val mBinding: RateRecyclerItemBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root),
        View.OnClickListener {
        init {
            mBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (sSelectedItems.get(getAdapterPosition(), false)) {
                sSelectedItems.delete(getAdapterPosition());
                mBinding.rateCard.setCardBackgroundColor(context!!.resources.getColor(R.color.white))
                mBinding.rate.setTextColor(context!!.resources.getColor(R.color.red))
                mBinding.divider.visibility = View.VISIBLE
            } else {
                sSelectedItems.put(checkPosition, false);
                sSelectedItems.put(getAdapterPosition(), true);

                mBinding.rateCard.setCardBackgroundColor(context!!.resources.getColor(R.color.red))
                mBinding.rate.setTextColor(context!!.resources.getColor(R.color.white))
                mBinding.divider.visibility = View.VISIBLE
            }
            checkPosition = getAdapterPosition()
            viewModel.rateCardPosition.value = checkPosition
            viewModel.rateCardValue.value = ratesList.get(checkPosition)
            notifyDataSetChanged()
        }
    }

}