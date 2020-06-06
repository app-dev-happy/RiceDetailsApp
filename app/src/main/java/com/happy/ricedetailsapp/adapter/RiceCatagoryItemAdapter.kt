package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.RiceCategoryItemLayoutBinding
import com.happy.ricedetailsapp.fragments.varietyPriceItemFragment
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.VarietyItem

class RiceCatagoryItemAdapter(val mContext: Context?, val riceVarietyList:ArrayList<VarietyItem>,val dashBoardMainPojo: DashBoardMainPojo) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding:RiceCategoryItemLayoutBinding=  DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.rice_category_item_layout,parent,false)
        return RiceCatagoryItemAdapterViewHolder(mContext,binding)
    }

    override fun getItemCount(): Int {
        return riceVarietyList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = riceVarietyList.get(position)
        val marg_10 = mContext?.resources!!.getDimensionPixelSize(R.dimen.text_size_10sp)
        val top=mContext?.resources!!.getDimensionPixelSize(R.dimen.text_size_10sp)
        val marg_16=mContext?.resources!!.getDimensionPixelSize(R.dimen.text_jioverify_success)
        val bottom=mContext?.resources!!.getDimensionPixelSize(R.dimen.text_size_10sp)
        val layoutParams = (holder as RiceCatagoryItemAdapterViewHolder).mBinding.root.layoutParams as ViewGroup.MarginLayoutParams
        if(position == 0){
            layoutParams.setMargins(marg_16, top, 0, bottom)
        }else if(position == (riceVarietyList.size-1) ) {
            layoutParams.setMargins(marg_10, top, marg_16, bottom)
        }
        else{
            layoutParams.setMargins(marg_10, top, 0, bottom)
        }
        (holder as RiceCatagoryItemAdapterViewHolder).mBinding.riceItemSubtext.text = item.title
        holder.mBinding.root.setOnClickListener {
            initFragment(item)
        }
    }
    private fun initFragment(item:VarietyItem) {
        try {
            val fragmentManager = (mContext as DashboardActivity).supportFragmentManager
            val varietyPriceItemFragment = varietyPriceItemFragment()
            varietyPriceItemFragment.setData(item,dashBoardMainPojo)
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, varietyPriceItemFragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class RiceCatagoryItemAdapterViewHolder(private val mContext: Context?,  val mBinding: RiceCategoryItemLayoutBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)

}