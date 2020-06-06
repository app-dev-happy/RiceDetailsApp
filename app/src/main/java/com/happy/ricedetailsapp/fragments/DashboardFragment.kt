package com.happy.ricedetailsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.databinding.LayoutFragmentDashboardBinding
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.DashboardMainContent
import com.happy.ricedetailsapp.viewModel.DashboardViewModel
import org.json.JSONException
import org.json.JSONObject

class DashboardFragment : Fragment(), View.OnClickListener {

    lateinit var layoutFragmentDashboardBinding: LayoutFragmentDashboardBinding
    internal lateinit var view : View
    lateinit var mDashboardViewModel: DashboardViewModel
    lateinit var adapter:DashboardMainRecyclerAdapter

    override fun onClick(view: View?) {   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutFragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_dashboard, container, false)
        layoutFragmentDashboardBinding.executePendingBindings()
        view =  layoutFragmentDashboardBinding.root
        mDashboardViewModel = ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        getFileData()
        init()
        return view
    }

    private fun init() {
        getCurrencyApiData()
        openScreen()
        initViews()
        initListener()
    }

    private fun getCurrencyApiData() {
            mDashboardViewModel.getCurrencyApiData(context!!).observe(requireActivity() as LifecycleOwner,
                Observer {
                    if(it!=null&&it.isNotEmpty()){
                        val response = JSONObject(it)
                        val ratesArray = response.getJSONObject("rates")
                        val currencyRatesMainPojo = Gson().fromJson(it, CurrencyRatesMainPojo::class.java)
                        val rates = currencyRatesMainPojo.rates
                        val typeOfHashMap = object : TypeToken<Map<String?, Double>?>() {}.type
                        val map : Map<String?, Double> =  Gson().fromJson(ratesArray.toString(), typeOfHashMap)
                        mDashboardViewModel.currencyRates.value = map
                        val rupeeFactor = rates.INR
                        mDashboardViewModel.dollarRupeeFactor.value = 1/rupeeFactor
                    }
                })
    }

    fun getFileData(){
        mDashboardViewModel.readDashboardFile(context!!).observe(requireActivity() as LifecycleOwner,
            Observer {
                if(it!=null&&it.length>0){
                    val dashBoardMainPojo = Gson().fromJson(it,DashBoardMainPojo::class.java)
                    val dashboardMainContent = dashBoardMainPojo.DashboardMainContent
                    setAdapter(dashboardMainContent,dashBoardMainPojo)
                }
            })
    }

    private fun setAdapter(dashboardMainContent:ArrayList<DashboardMainContent>,dashBoardMainPojo: DashBoardMainPojo) {
        adapter = DashboardMainRecyclerAdapter(context,dashboardMainContent,dashBoardMainPojo)
        layoutFragmentDashboardBinding.mainRecycler.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutFragmentDashboardBinding.mainRecycler.adapter = adapter
    }

    private fun openScreen() {
        try {
            layoutFragmentDashboardBinding.root.animation =
                AnimationUtils.loadAnimation(context, R.anim.slide_up)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initListener() {

    }

    private fun initViews() {

    }


}