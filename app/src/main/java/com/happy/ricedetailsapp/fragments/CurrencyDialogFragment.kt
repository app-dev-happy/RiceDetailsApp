package com.happy.ricedetailsapp.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.CurrencyDialogAdapter
import com.happy.ricedetailsapp.databinding.FragmentCurrencyDialogBinding
import com.happy.ricedetailsapp.pojo.CurrencyContent
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class CurrencyDialogFragment : DialogFragment() {
    lateinit var mBinding:FragmentCurrencyDialogBinding
    lateinit var adapter:CurrencyDialogAdapter
    lateinit var mDashboardViewModel: DashboardViewModel
    var currencyItem: CurrencyContent?=null
    var checkPosition: Int?=0
    var currencyList = ArrayList<CurrencyContent>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_currency_dialog,container,false)
        mBinding.executePendingBindings()
        mDashboardViewModel = ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        init()
        return mBinding.root
    }
    override fun onStart() {
        super.onStart()
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val li_height = metrics.heightPixels * 50 / 100
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            li_height
        )
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = STYLE_NO_TITLE
        val theme = android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth
        setStyle(style, theme)
    }

    fun init(){
        initDialog()
        setAdapter()
        initListner()
    }

    private fun initListner() {
        mBinding.currencyCancel.setOnClickListener(View.OnClickListener {
            dialog!!.dismiss()
        })
        mBinding.currencySubmitBtn.setOnClickListener(View.OnClickListener {
            if(currencyItem?.key!=null&&!mDashboardViewModel.selectedCurrencyKey.value.equals(currencyItem?.key)) {
                if(mDashboardViewModel.currencyRates.value!!.containsKey(currencyItem?.key)){
                mDashboardViewModel.selectedCurrencyKey.value = currencyItem?.key
                mDashboardViewModel.selectedCurrencySymbol.value = currencyItem?.symbol
                }
            }
            if(checkPosition!=mDashboardViewModel.checkedPosition.value){
                mDashboardViewModel.checkedPosition.value = checkPosition
            }
            dialog!!.dismiss()
        })
    }

    private fun setAdapter() {
        adapter = CurrencyDialogAdapter(context!!,this,currencyList,mDashboardViewModel.checkedPosition.value)
        mBinding.currencyRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.currencyRecycler.adapter = adapter
    }

    private fun initDialog() {
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        dialog!!.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog!!.setCanceledOnTouchOutside(false)
    }

    fun setData(currencyItem: CurrencyContent, checkPosition: Int?) {
        this.currencyItem = currencyItem
        this.checkPosition = checkPosition
    }

    fun setDataList(currencyList: ArrayList<CurrencyContent>?) {
            this.currencyList = currencyList!!
    }
}
