package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.databinding.CurrencyDialogItemLayoutBinding
import com.happy.ricedetailsapp.fragments.CurrencyDialogFragment
import com.happy.ricedetailsapp.pojo.CurrencyContent

class CurrencyDialogAdapter(val mContext: Context,val fragment:CurrencyDialogFragment,val currencyContent:ArrayList<CurrencyContent>,var checkPosition:Int?):androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var binding:CurrencyDialogItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        binding = CurrencyDialogItemLayoutBinding.inflate(inflter,parent,false)
        return CurrencyDialogAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
        return currencyContent.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val currencyItem = currencyContent.get(position)
        if((currencyContent.size-1)==position){
            (holder as CurrencyDialogAdapterViewHolder).mBinding.divideLine.visibility = View.GONE
        }
        (holder as CurrencyDialogAdapterViewHolder).boo(currencyItem)
    }
    inner class CurrencyDialogAdapterViewHolder(private val mContext: Context, val mBinding: CurrencyDialogItemLayoutBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root){
        fun boo(currencyItem:CurrencyContent){
            if (checkPosition == -1) {
                mBinding.isSelected.visibility =View.GONE
            } else {
                if (checkPosition == adapterPosition) {
                    mBinding.isSelected.visibility =View.VISIBLE
                } else {
                    mBinding.isSelected.visibility =View.GONE
                }
            }
            mBinding.currencyValue.text = currencyItem.title
            mBinding.clickCurrency.setOnClickListener(View.OnClickListener {
                mBinding.isSelected.visibility =View.VISIBLE
                if (checkPosition != adapterPosition) {

                    notifyItemChanged(checkPosition!!)
                    checkPosition = adapterPosition
                    fragment.setData(currencyItem,checkPosition)
                }
              }
            )
        }
    }

}