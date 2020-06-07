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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.PackagingItemAdapter
import com.happy.ricedetailsapp.adapter.RateCardsItemAdapter
import com.happy.ricedetailsapp.databinding.LayoutCategoryDetailsBinding
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.Rates
import com.happy.ricedetailsapp.pojo.VarietyItem
import com.happy.ricedetailsapp.viewModel.DashboardViewModel
import com.squareup.picasso.Picasso

class CategoryDetaillsFragment : Fragment() {
    var varietyItem:VarietyItem?=null
    lateinit var mBinding:LayoutCategoryDetailsBinding
    var dashBoardMainPojo:DashBoardMainPojo?=null
    lateinit var mDashboardViewModel: DashboardViewModel
    private var currencyDialogueFragment = CurrencyDialogFragment()
    private var seaPortDialogFragment = SeaPortDialogFragment()
    lateinit var packagingAdapter:PackagingItemAdapter
     var ratesAdapter:RateCardsItemAdapter?=null
    var kgsBtnSelected:Boolean = true
    var lbsBtnSelected:Boolean = false
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
        initViews()
        setPackagingAdapter()
        setRateAdapter()
    }

     fun initViews() {
         mDashboardViewModel.seaPortPosition.observe(requireActivity() as LifecycleOwner, Observer {
             if(it!=null){
                 val currencyFactor = mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value)
                 mBinding.seaportOption.text = dashBoardMainPojo?.SeaPortContent!!.get(it).title
                 val number =(dashBoardMainPojo?.SeaPortContent!!.get(it).stdPrice.toInt()+varietyItem!!.stdPrice.toInt())*currencyFactor!!
                 val number3digits:Double = Math.round(number * 1000.0) / 1000.0
                 val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
                 mBinding.price.text = mDashboardViewModel.selectedCurrencySymbol.value.toString() + number2digits.toString()
             }
         })
         mDashboardViewModel.checkedPosition.observe(requireActivity() as LifecycleOwner, Observer {
             val currencyFactor = mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value)
             val number = (((dashBoardMainPojo?.SeaPortContent!!.get( mDashboardViewModel.seaPortPosition.value!!).stdPrice.toInt()+varietyItem!!.stdPrice.toInt())*currencyFactor!!))
             val number3digits:Double = Math.round(number * 1000.0) / 1000.0
             val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
             mBinding.price.text = mDashboardViewModel.selectedCurrencySymbol.value.toString() + number2digits.toString()
             if(ratesAdapter!=null) {
                 if (kgsBtnSelected) {
                     ratesAdapter!!.setData(varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,kgsBtnSelected)
                 } else {
                     ratesAdapter!!.setData(varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).lbsWeightItem,kgsBtnSelected)
                 }
             }
         })
         Picasso.get().load("https://images.ctfassets.net/3s5io6mnxfqz/6R1SuUg4ng0zFEAcUjaoO1/e5b55d7b48b4c4e3227ac1532e62b9eb/AdobeStock_112422230.jpeg").into(mBinding.categoryImg);
    }
    private fun initListner() {
        mBinding.currencyIcon.setOnClickListener {
            initCurrencyDialogFragment()
        }
        mBinding.seaPortContainer.setOnClickListener {
            initSeaPortDialogFragment()
        }
        mBinding.backIcon.setOnClickListener {
            (context as DashboardActivity).onBackPressed()
        }
        mBinding.kgsBtn.setOnClickListener {
            kgsBtnSelected = true
            lbsBtnSelected = false
            mBinding.kgsBtn.setTextColor(context!!.resources.getColor(R.color.white))
            mBinding.lbsBtn.setTextColor(context!!.resources.getColor(R.color.black))
            mBinding.kgsBtn.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
            mBinding.lbsBtn.background = context!!.resources.getDrawable(R.drawable.white_rounded_bg)
            ratesAdapter!!.setData(varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,kgsBtnSelected)
        }
        mBinding.lbsBtn.setOnClickListener {
            kgsBtnSelected = false
            lbsBtnSelected = true
            mBinding.lbsBtn.setTextColor(context!!.resources.getColor(R.color.white))
            mBinding.lbsBtn.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
            mBinding.kgsBtn.background = context!!.resources.getDrawable(R.drawable.white_rounded_bg)
            mBinding.kgsBtn.setTextColor(context!!.resources.getColor(R.color.black))
            ratesAdapter!!.setData(varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).lbsWeightItem,kgsBtnSelected)
        }
    }

    private fun initCurrencyDialogFragment() {
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
    private fun initSeaPortDialogFragment() {
        try {
            if (!seaPortDialogFragment.isVisible&&!seaPortDialogFragment.isAdded()) {
                seaPortDialogFragment.setDataList(dashBoardMainPojo?.SeaPortContent)
                seaPortDialogFragment.show(
                    (context as DashboardActivity).supportFragmentManager,
                    "SeaPortfrag"
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

    fun setPackagingAdapter(){
        packagingAdapter = PackagingItemAdapter(this,varietyItem!!.packing,mDashboardViewModel.packagingPosition.value!!,context!!)
        mBinding.packagingRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mBinding.packagingRecycler.adapter = packagingAdapter
    }

    fun setRateAdapter(){
        mDashboardViewModel.packagingPosition.observe(requireActivity() as LifecycleOwner, Observer {
            if(kgsBtnSelected){
                ratesAdapter = RateCardsItemAdapter(this,context!!)
            }
            else{
                ratesAdapter = RateCardsItemAdapter(this,context!!)
            }
          }
        )
        ratesAdapter!!.setData(varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,kgsBtnSelected)
        mBinding.rateRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rateRecycler.adapter = ratesAdapter
    }

    fun setPackingPosition(checkPosition: Int) {
        if(kgsBtnSelected){
            ratesAdapter!!.setData(varietyItem!!.packing.get(checkPosition).kgsWeightItem,kgsBtnSelected)
        }
        else{
            ratesAdapter!!.setData(varietyItem!!.packing.get(checkPosition).lbsWeightItem,kgsBtnSelected)
        }
    }
}
