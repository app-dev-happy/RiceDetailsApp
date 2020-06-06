package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import android.provider.Telephony
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.LayoutCategoryDetailsBinding
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.Rates
import com.happy.ricedetailsapp.pojo.VarietyItem
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class CategoryDetaillsFragment : Fragment() {
    var varietyItem:VarietyItem?=null
    lateinit var mBinding:LayoutCategoryDetailsBinding
    var dashBoardMainPojo:DashBoardMainPojo?=null
    lateinit var mDashboardViewModel: DashboardViewModel
    private var currencyDialogueFragment = CurrencyDialogFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_category_details, container, false)
        mBinding.executePendingBindings()
        mDashboardViewModel = ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        init()
        return mBinding.root
    }
    fun init(){
        initListner()
    }

    private fun initListner() {
        mBinding.currencyIcon.setOnClickListener {
            if(mDashboardViewModel.currenctRates.value.toString().length<0){
            mDashboardViewModel.getCurrencyApiData(context!!).observe(requireActivity() as LifecycleOwner,
                Observer {
                    if(it!=null&&it.isNotEmpty()){
                        val currencyRatesMainPojo = Gson().fromJson(it,CurrencyRatesMainPojo::class.java)
                        val rates = currencyRatesMainPojo.rates
                        mDashboardViewModel.currenctRates.value = rates
                    }
                })}
            initDialogFragment()
        }
    }

    private fun initDialogFragment() {
        try {
            if (!currencyDialogueFragment.isVisible&&!currencyDialogueFragment.isAdded()) {
                currencyDialogueFragment.setDataList(dashBoardMainPojo?.CurrencyContent)
                currencyDialogueFragment.show(
                    (context as DashboardActivity).supportFragmentManager,
                    "currencyfrag"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setData(varietyItem: VarietyItem,dashBoardMainPojo: DashBoardMainPojo) {
        this.varietyItem = varietyItem
        this.dashBoardMainPojo = dashBoardMainPojo
    }
}
