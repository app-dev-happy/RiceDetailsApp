package com.happy.ricedetailsapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happy.ricedetailsapp.FileDataCoroutines.FileDataCoroutines
import kotlinx.coroutines.*

class DashboardViewModel: ViewModel() {

    fun readDashboardFile(context:Context): LiveData<String> {
        var mDashboardFileLiveData: MutableLiveData<String> = MutableLiveData<String>()
        val url = "https://ricedetailsapp.free.beeceptor.com/my/api/path"
        try{
        CoroutineScope(Dispatchers.IO).launch {
            val job = async { FileDataCoroutines().getDataFromServer(url, context) }
            val mCoroutineResponse = job.await()
            withContext(Dispatchers.Main){
                if(mCoroutineResponse.status == 0){
                    if(mCoroutineResponse.dataString!=null&&mCoroutineResponse.dataString!!.length>0)
                        mDashboardFileLiveData.value = mCoroutineResponse.dataString
                }
            }
        }
    } catch (ex: Exception) {
            ex.printStackTrace()
    }
        return mDashboardFileLiveData
    }
}