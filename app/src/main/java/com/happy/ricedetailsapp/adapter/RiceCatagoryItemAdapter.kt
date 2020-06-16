package com.happy.ricedetailsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.RiceCategoryItemLayoutBinding
import com.happy.ricedetailsapp.fragments.CategoryDetaillsFragment
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.VarietyItem
import com.squareup.picasso.Picasso

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
        val marg_10=mContext?.resources!!.getDimensionPixelSize(R.dimen.marg_10)
        val marg_14=mContext?.resources!!.getDimensionPixelSize(R.dimen.marg_14)
        val layoutParams = (holder as RiceCatagoryItemAdapterViewHolder).mBinding.root.layoutParams as ViewGroup.MarginLayoutParams
        if(position == 0){
            layoutParams.setMargins(marg_14, 0, 0, 0)
        }else if(position == (riceVarietyList.size-1) ) {
            layoutParams.setMargins(0, 0, marg_10, 0)
        }
        else{
            layoutParams.setMargins(0, 0, 0, 0)
        }
        if (item.iconURL.isNotEmpty()){
            Picasso.get().load(item.iconURL).into( holder.mBinding.ricecategoryimg)
        }
        else {
            Picasso.get()
                .load("https://images.ctfassets.net/3s5io6mnxfqz/6R1SuUg4ng0zFEAcUjaoO1/e5b55d7b48b4c4e3227ac1532e62b9eb/AdobeStock_112422230.jpeg")
                .into( holder.mBinding.ricecategoryimg);
        }
        (holder as RiceCatagoryItemAdapterViewHolder).mBinding.riceItemSubtext.text = item.title
        holder.mBinding.root.setOnClickListener {
            initFragment(item)
        }
    }
    private fun initFragment(item:VarietyItem) {
        try {
            val fragmentManager = (mContext as DashboardActivity).supportFragmentManager
            val varietyPriceItemFragment = CategoryDetaillsFragment()
            varietyPriceItemFragment.setData(item,dashBoardMainPojo)
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, varietyPriceItemFragment).addToBackStack(null).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class RiceCatagoryItemAdapterViewHolder(private val mContext: Context?,  val mBinding: RiceCategoryItemLayoutBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(mBinding!!.root)

}