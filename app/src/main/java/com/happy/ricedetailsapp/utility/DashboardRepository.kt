package com.happy.ricedetailsapp.utility

import androidx.lifecycle.MutableLiveData
import com.happy.ricedetailsapp.coroutines.CoroutinesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object DashboardRepository {
    var mVersionFileLiveData: MutableLiveData<CoroutinesResponse> = MutableLiveData<CoroutinesResponse>()
    fun loadVersionFileNew(){

    }

}