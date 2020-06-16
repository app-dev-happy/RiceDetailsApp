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
import com.happy.ricedetailsapp.adapter.SeaPortDialogAdapter
import com.happy.ricedetailsapp.databinding.SeaPortDialogLayoutBinding
import com.happy.ricedetailsapp.pojo.CurrencyContent
import com.happy.ricedetailsapp.pojo.SeaPortContent
import com.happy.ricedetailsapp.viewModel.DashboardViewModel

class SeaPortDialogFragment : DialogFragment() {
    lateinit var mBinding:SeaPortDialogLayoutBinding
    lateinit var adapter:SeaPortDialogAdapter
    lateinit var mDashboardViewModel: DashboardViewModel
    var seaPortItem: SeaPortContent?=null
    var checkPosition: Int?=0
    var seaPortList = ArrayList<SeaPortContent>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.sea_port_dialog_layout,container,false)
        mBinding.executePendingBindings()
        mDashboardViewModel = ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        init()
        return mBinding.root
    }
    override fun onStart() {
        super.onStart()
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val li_height = metrics.heightPixels * 70 / 100
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            li_height
        )
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.dialog_backgroud_rounded)
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
        mBinding.seaPortCancel.setOnClickListener(View.OnClickListener {
            seaPortItem = null
            checkPosition = -1
            dialog!!.dismiss()
        })
        mBinding.seaPortSubmitBtn.setOnClickListener(View.OnClickListener {
            if(checkPosition!=mDashboardViewModel.seaPortPosition.value&&checkPosition!=-1){
                mDashboardViewModel.seaPortPosition.value = checkPosition
            }
            dialog!!.dismiss()
        })
    }

    private fun setAdapter() {
        adapter = SeaPortDialogAdapter(context!!,this,seaPortList,mDashboardViewModel.seaPortPosition.value)
        mBinding.seaPortRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.seaPortRecycler.adapter = adapter
    }

    private fun initDialog() {
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        dialog!!.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog!!.setCanceledOnTouchOutside(true)
    }

    fun setData(seaPortItem: SeaPortContent, checkPosition: Int?) {
        this.seaPortItem = seaPortItem
        this.checkPosition = checkPosition
    }

    fun setDummyData(seaPortItem: SeaPortContent?, checkPosition: Int?) {
        this.seaPortItem = seaPortItem
        this.checkPosition = checkPosition
    }

    fun setDataList(seaPortItemList: ArrayList<SeaPortContent>?) {
        this.seaPortList = seaPortItemList!!
    }
}
