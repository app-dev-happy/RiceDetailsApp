package com.happy.ricedetailsapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.PackagingItemAdapter
import com.happy.ricedetailsapp.adapter.RateCardsItemAdapter
import com.happy.ricedetailsapp.databinding.LayoutCategoryDetailsBinding
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.KgsWeightItem
import com.happy.ricedetailsapp.pojo.VarietyItem
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.utility.PDFTools
import com.happy.ricedetailsapp.viewModel.DashboardViewModel
import com.squareup.picasso.Picasso


class CategoryDetaillsFragment : Fragment() {
    var varietyItem: VarietyItem? = null
    lateinit var mBinding: LayoutCategoryDetailsBinding
    var dashBoardMainPojo: DashBoardMainPojo? = null
    lateinit var mDashboardViewModel: DashboardViewModel
    private var currencyDialogueFragment = CurrencyDialogFragment()
    private var seaPortDialogFragment = SeaPortDialogFragment()
    lateinit var packagingAdapter: PackagingItemAdapter
    var packagingPosition = 0
    var ratesAdapter: RateCardsItemAdapter? = null
    var dollorToRsFactor:Double = 0.0
    var kgsBtnSelected: Boolean = true
    var lbsItemList =ArrayList<KgsWeightItem>()
    var lbsBtnSelected: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_category_details, container, false)
        mBinding.executePendingBindings()
        mDashboardViewModel =
            ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        init()
        return mBinding.root
    }

    fun init() {
        if( mDashboardViewModel.currencyRates.value!=null)
        dollorToRsFactor = 24*(mDashboardViewModel.currencyRates.value!!.get("USD")!!)
            mBinding.infoIcon.visibility = View.VISIBLE
        initListner()
        initViews()
        setPackagingAdapter()
        setRateAdapter()
    }

    fun initViews() {
        if (mDashboardViewModel.rateCardValue.value == null) {
            mDashboardViewModel.rateCardValue.value =
                varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem.get(
                    0
                )
        }
        mDashboardViewModel.seaPortPosition.observe(requireActivity() as LifecycleOwner, Observer {
            if (it != null) {
                val currencyFactor =
                    mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value)
                mBinding.seaportOption.text = dashBoardMainPojo?.seaPortContent!!.get(it).title
                var rateCardValue = 0.0
                if(kgsBtnSelected){
                    rateCardValue =
                        (100 / (mDashboardViewModel.rateCardValue.value!!.weight.toInt())) * (mDashboardViewModel.rateCardValue.value!!.price.toDouble())
                }else{
                    rateCardValue =
                        (220.5 / (mDashboardViewModel.rateCardValue.value!!.weight.toInt())) * (mDashboardViewModel.rateCardValue.value!!.price.toDouble())
                }
                val number =
                    (((dashBoardMainPojo?.seaPortContent!!.get(it).stdPrice.toInt()/10*dollorToRsFactor).toInt() + varietyItem!!.stdPrice.toInt() + rateCardValue) * 10) * currencyFactor!!
                val number3digits: Double = Math.round(number * 1000.0) / 1000.0
                val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
                mBinding.price.text =
                    mDashboardViewModel.selectedCurrencySymbol.value.toString() + number2digits.toString()
            }
        })
        mDashboardViewModel.checkedPosition.observe(requireActivity() as LifecycleOwner, Observer {
            val currencyFactor =
                mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value)
            var rateCardValue = 0.0
            if(kgsBtnSelected){
                 rateCardValue =
                    (100 / (mDashboardViewModel.rateCardValue.value!!.weight.toInt())) * (mDashboardViewModel.rateCardValue.value!!.price.toDouble())
            }else{
                 rateCardValue =
                    (220.5 / (mDashboardViewModel.rateCardValue.value!!.weight.toInt())) * (mDashboardViewModel.rateCardValue.value!!.price.toDouble())
            }
            val number =
                (((((dashBoardMainPojo?.seaPortContent!!.get(mDashboardViewModel.seaPortPosition.value!!).stdPrice.toInt()/10*dollorToRsFactor).toInt() + varietyItem!!.stdPrice.toInt() + rateCardValue) * 10) * currencyFactor!!))
            val number3digits: Double = Math.round(number * 1000.0) / 1000.0
            val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
            mBinding.price.text =
                mDashboardViewModel.selectedCurrencySymbol.value.toString() + number2digits.toString()
            if (ratesAdapter != null) {
                if (kgsBtnSelected) {
                    ratesAdapter!!.setData(
                        varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,
                        kgsBtnSelected, mDashboardViewModel.rateCardPosition.value!!,mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value), mDashboardViewModel.selectedCurrencySymbol.value
                    )
                } else {
                    ratesAdapter!!.setData(
                        varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).lbsWeightItem,
                        kgsBtnSelected,
                        mDashboardViewModel.rateCardPosition.value!!,
                        mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
                        mDashboardViewModel.selectedCurrencySymbol.value
                    )
                }
            }
        })
        mDashboardViewModel.rateCardValue.observe(requireActivity() as LifecycleOwner, Observer {
            if (it != null) {
                val currencyFactor =
                    mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value)
                var rateCardValue = 0.0
                if(kgsBtnSelected){
                    rateCardValue =
                        (100/ (it.weight.toInt()))* (it.price.toDouble())
                }else{
                    rateCardValue =
                        (220.5/ (it.weight.toInt()))* (it.price.toDouble())
                }
                val number =
                    (((dashBoardMainPojo?.seaPortContent!!.get(mDashboardViewModel.seaPortPosition.value!!).stdPrice.toInt()/10*dollorToRsFactor).toInt() + varietyItem!!.stdPrice.toInt() + rateCardValue.toInt()) * 10) * currencyFactor!!
                val number3digits: Double = Math.round(number * 1000.0) / 1000.0
                val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
                mBinding.price.text =
                    mDashboardViewModel.selectedCurrencySymbol.value.toString() + number2digits.toString()
            }
        })
        if (varietyItem!!.iconURL.isNotEmpty()) {
            Picasso.get().load(varietyItem!!.iconURL).into(mBinding.categoryImg)
        } else {
            Picasso.get()
                .load(R.drawable.rice_grain_fade_logo)
                .into(mBinding.categoryImg);
        }
    }

    private fun initListner() {
        mBinding.currencyIcon.setOnClickListener {
            initCurrencyDialogFragment()
        }
        mBinding.infoIcon.setOnClickListener {
            initInformationFragment()
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
            mBinding.lbsBtn.background =
                context!!.resources.getDrawable(R.drawable.white_rounded_bg)
            ratesAdapter!!.setData(
                varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,
                kgsBtnSelected,
                0,
                mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
                mDashboardViewModel.selectedCurrencySymbol.value
            )
        }
        mBinding.lbsBtn.setOnClickListener {
            kgsBtnSelected = false
            lbsBtnSelected = true
            mBinding.lbsBtn.setTextColor(context!!.resources.getColor(R.color.white))
            mBinding.lbsBtn.background = context!!.resources.getDrawable(R.drawable.red_rounded_bg)
            mBinding.kgsBtn.background =
                context!!.resources.getDrawable(R.drawable.white_rounded_bg)
            mBinding.kgsBtn.setTextColor(context!!.resources.getColor(R.color.black))
            ratesAdapter!!.setData(
                varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).lbsWeightItem,
                kgsBtnSelected,
                0,
                mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
                mDashboardViewModel.selectedCurrencySymbol.value
            )
        }
    }
    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                === PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("per", "Permission is granted")
                true
            } else {
                Log.v("per", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("per", "Permission is granted")
            true
        }
    }
    fun initInformationFragment(){
        val fragmentManager = (context as DashboardActivity).supportFragmentManager
        val informationFragment = InformationFragment()
        if(dashBoardMainPojo!=null&&dashBoardMainPojo?.detailsContent!=null&&dashBoardMainPojo?.detailsContent!!.size>0){
            informationFragment.setData( varietyItem!!.specifications)
        }
        var openFragment = fragmentManager.beginTransaction()
        openFragment.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
        openFragment.replace(R.id.fragmentContainer, informationFragment).addToBackStack(null)
            .commit()
    }

    private fun initCurrencyDialogFragment() {
        try {
            if (!currencyDialogueFragment.isVisible && !currencyDialogueFragment.isAdded()) {
                currencyDialogueFragment.setDummyData(null, -1)
                currencyDialogueFragment.setDataList(dashBoardMainPojo?.currencyContent)
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
            if (!seaPortDialogFragment.isVisible && !seaPortDialogFragment.isAdded()) {
                seaPortDialogFragment.setDummyData(null, -1)
                seaPortDialogFragment.setDataList(dashBoardMainPojo?.seaPortContent)
                seaPortDialogFragment.show(
                    (context as DashboardActivity).supportFragmentManager,
                    "SeaPortfrag"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setData(varietyItem: VarietyItem, dashBoardMainPojo: DashBoardMainPojo) {
        this.varietyItem = varietyItem
        this.dashBoardMainPojo = dashBoardMainPojo
    }

    fun setPackagingAdapter() {
        packagingAdapter = PackagingItemAdapter(
            this,
            varietyItem!!.packing,
            mDashboardViewModel.packagingPosition.value!!,
            context!!
        )
        mBinding.packagingRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mBinding.packagingRecycler.adapter = packagingAdapter
    }

    fun setRateAdapter() {
        mDashboardViewModel.packagingPosition.observe(
            requireActivity() as LifecycleOwner,
            Observer {
                if(ratesAdapter!=null) {
                    if (kgsBtnSelected) {
                        ratesAdapter!!.setData(
                            varietyItem!!.packing.get(it).kgsWeightItem,
                            kgsBtnSelected,
                            0,
                            mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
                            mDashboardViewModel.selectedCurrencySymbol.value
                        )
                    } else {
                        ratesAdapter!!.setData(
                            varietyItem!!.packing.get(it).lbsWeightItem,
                            kgsBtnSelected,
                            0,
                            mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
                            mDashboardViewModel.selectedCurrencySymbol.value
                        )
                    }
                }
            }
        )
        ratesAdapter = RateCardsItemAdapter(this, context!!)
        ratesAdapter!!.setData(
            varietyItem!!.packing.get(mDashboardViewModel.packagingPosition.value!!).kgsWeightItem,
            kgsBtnSelected,
            mDashboardViewModel.rateCardPosition.value!!,
            mDashboardViewModel.currencyRates.value!!.get(mDashboardViewModel.selectedCurrencyKey.value),
            mDashboardViewModel.selectedCurrencySymbol.value
        )
        mBinding.rateRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rateRecycler.adapter = ratesAdapter
    }

    fun setPackingPosition(checkPosition: Int) {
        mDashboardViewModel.packagingPosition.value = checkPosition
    }

    fun setRateCardData(checkPosition: Int, item: KgsWeightItem) {
        mDashboardViewModel.rateCardPosition.value = checkPosition
        mDashboardViewModel.rateCardValue.value = item
    }
}
