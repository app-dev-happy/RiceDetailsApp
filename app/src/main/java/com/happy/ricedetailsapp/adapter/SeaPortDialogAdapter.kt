package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.CurrencyDialogItemLayoutBinding
import com.happy.ricedetailsapp.fragments.CurrencyDialogFragment
import com.happy.ricedetailsapp.fragments.SeaPortDialogFragment
import com.happy.ricedetailsapp.pojo.CurrencyContent
import com.happy.ricedetailsapp.pojo.SeaPortContent

class SeaPortDialogAdapter(
    val mContext: Context,
    val fragment: SeaPortDialogFragment,
    val seaPortContent: ArrayList<SeaPortContent>,
    var checkPosition: Int?
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding: CurrencyDialogItemLayoutBinding
    var sSelectedItems = SparseBooleanArray()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = CurrencyDialogItemLayoutBinding.inflate(inflter, parent, false)
        return CurrencyDialogAdapterViewHolder(mContext, binding)
    }

    init {
        sSelectedItems.clear()
    }

    override fun getItemCount(): Int {
        return seaPortContent.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val seaPortItem = seaPortContent.get(position)
        if (sSelectedItems.get(position) || checkPosition == position) {
            (holder as CurrencyDialogAdapterViewHolder).mBinding.isSelected.visibility =
                View.VISIBLE
        } else {
            (holder as CurrencyDialogAdapterViewHolder).mBinding.isSelected.visibility = View.GONE
        }
        if ((seaPortContent.size - 1) == position) {
            (holder as CurrencyDialogAdapterViewHolder).mBinding.divideLine.visibility = View.GONE
        } else {
            (holder as CurrencyDialogAdapterViewHolder).mBinding.divideLine.visibility =
                View.VISIBLE
        }
        (holder as CurrencyDialogAdapterViewHolder).mBinding.currencyValue.text = seaPortItem.title
    }

    inner class CurrencyDialogAdapterViewHolder(
        private val mContext: Context,
        val mBinding: CurrencyDialogItemLayoutBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root),
        View.OnClickListener {
        init {
            mBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (sSelectedItems.get(getAdapterPosition(), false)) {
                sSelectedItems.delete(getAdapterPosition());
                mBinding.isSelected.visibility = View.VISIBLE
            } else {
                sSelectedItems.put(checkPosition!!, false);
                sSelectedItems.put(getAdapterPosition(), true);
                mBinding.isSelected.visibility = View.GONE
            }
            checkPosition = getAdapterPosition()
            fragment.setData(seaPortContent.get(checkPosition!!), checkPosition)
            notifyDataSetChanged()
        }
    }

}