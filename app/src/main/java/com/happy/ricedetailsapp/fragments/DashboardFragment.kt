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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.adapter.DashboardMainRecyclerAdapter
import com.happy.ricedetailsapp.databinding.LayoutFragmentDashboardBinding
import com.happy.ricedetailsapp.pojo.CurrencyRatesMainPojo
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.DashboardMainContent
import com.happy.ricedetailsapp.pojo.SeaPortContent
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.utility.DashboardRepository
import com.happy.ricedetailsapp.viewModel.DashboardViewModel
import org.json.JSONObject
import java.lang.Exception

class DashboardFragment : Fragment(), View.OnClickListener {

    lateinit var layoutFragmentDashboardBinding: LayoutFragmentDashboardBinding
    internal lateinit var view: View
    lateinit var mDashboardViewModel: DashboardViewModel
    lateinit var adapter: DashboardMainRecyclerAdapter
    val mRef = FirebaseDatabase.getInstance().reference
    override fun onClick(view: View?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutFragmentDashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_fragment_dashboard, container, false)
        layoutFragmentDashboardBinding.executePendingBindings()
        view = layoutFragmentDashboardBinding.root
        mDashboardViewModel =
            ViewModelProviders.of(this.requireActivity()).get(DashboardViewModel::class.java)
        mDashboardViewModel.packagingPosition.value = 0
        mDashboardViewModel.rateCardPosition.value=0
//        getFileData()
        getCurrencyData()
        init()
        return view
    }

    fun getCurrencyData() {
        try {
            mDashboardViewModel.getDbCurrencyFile(requireContext())
                .observe(requireActivity() as LifecycleOwner,
                    Observer {
                        if (it != null) {
                            val response = JSONObject(it)
                            val ratesArray = response.getJSONObject("rates")
                            val typeOfHashMap =
                                object : TypeToken<Map<String?, Double>?>() {}.type
                            val map: Map<String?, Double> =
                                Gson().fromJson(ratesArray.toString(), typeOfHashMap)
                            mDashboardViewModel.currencyRates.value = map
                        }
                    })
        }catch (e:Exception){
                e.printStackTrace()
            }
    }

    private fun init() {
        mDashboardViewModel.rateCardPosition.value = 0
        mDashboardViewModel.rateCardValue.value = null
        initViews()
    }

    fun getFileData() {
        try {
            mDashboardViewModel.getDbDashboardFile(requireContext())
                .observe(requireActivity() as LifecycleOwner,
                    Observer {
                        if (it != null) {
                            val dashBoardMainPojo = Gson().fromJson(it,DashBoardMainPojo::class.java)
                            val dashboardMainContent =   dashBoardMainPojo.dashboardMainContent
                            if(dashboardMainContent!=null&&dashboardMainContent.size>0) {
                                setAdapter(dashboardMainContent, dashBoardMainPojo)
                                if (dashBoardMainPojo.clearancePortContent != null && dashBoardMainPojo.clearancePortContent.isNotEmpty()) {
                                    val clearancePortContent = dashBoardMainPojo.clearancePortContent
                                    if (clearancePortContent.size > 0)
                                        initClearance(clearancePortContent)
                                }
                            }
                        }
                    })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setAdapter(
        dashboardMainContent: ArrayList<DashboardMainContent>,
        dashBoardMainPojo: DashBoardMainPojo
    ) {
        adapter = DashboardMainRecyclerAdapter(context, dashboardMainContent, dashBoardMainPojo)
        layoutFragmentDashboardBinding.mainRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutFragmentDashboardBinding.mainRecycler.adapter = adapter
    }

    private fun initClearance(clearancePortContent: ArrayList<SeaPortContent>) {
        layoutFragmentDashboardBinding.clearanceFab.visibility = View.VISIBLE
        layoutFragmentDashboardBinding.clearanceFab.setOnClickListener {
            initClearanceFragment(clearancePortContent)
        }
    }

    fun initClearanceFragment(clearancePortContent: ArrayList<SeaPortContent>) {
        val fragmentManager = (context as DashboardActivity).supportFragmentManager
        val clearanceFragment = ClearanceFragment()
        clearanceFragment.setData(clearancePortContent)
        var openFragment = fragmentManager.beginTransaction()
        openFragment.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
        openFragment.replace(R.id.fragmentContainer, clearanceFragment).addToBackStack(null)
            .commit()
    }

    private fun initViews() {

    }

    override fun onStart() {
        super.onStart()
        mRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val jsonString: String = Gson().toJson(snapshot.getValue())
                if (jsonString.length>0) {
                    val dashBoardMainPojo = Gson().fromJson(jsonString,DashBoardMainPojo::class.java)
                    val dashboardMainContent =   dashBoardMainPojo.dashboardMainContent
                    if(dashboardMainContent!=null&&dashboardMainContent.size>0) {
                        setAdapter(dashboardMainContent, dashBoardMainPojo)
                        if (dashBoardMainPojo.clearancePortContent != null && dashBoardMainPojo.clearancePortContent.isNotEmpty()) {
                            val clearancePortContent = dashBoardMainPojo.clearancePortContent
                            if (clearancePortContent.size > 0)
                                initClearance(clearancePortContent)
                        }
                    }
                }
            }
        })
    }
}