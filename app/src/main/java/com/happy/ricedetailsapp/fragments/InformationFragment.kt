package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.ClearanceAdapter
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.databinding.LayoutClearanceBinding
import com.happy.ricedetailsapp.databinding.LayoutInformationBinding
import com.happy.ricedetailsapp.pojo.DetailsContent
import com.happy.ricedetailsapp.pojo.SeaPortContent

class InformationFragment : Fragment() {
    lateinit var mBinding: LayoutInformationBinding
    var detailsContent = ArrayList<DetailsContent>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_information, container, false)
        mBinding.executePendingBindings()
        init()
        return mBinding.root
    }

    fun init() {
        if(detailsContent[0].subTitle.isNotEmpty()&&detailsContent.get(0)!=null&&detailsContent.get(0).subTitle!=null)
        mBinding.descLine.text = detailsContent[0].subTitle
        initListner()
    }

    private fun initListner() {
        mBinding.backIcon.setOnClickListener {
            (context as DashboardActivity).onBackPressed()
        }
    }

    fun setData(detailsContent: ArrayList<DetailsContent>) {
        this.detailsContent = detailsContent
    }
}