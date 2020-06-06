package com.happy.ricedetailsapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happy.ricedetailsapp.FileDataCoroutines.FileDataCoroutines
import com.happy.ricedetailsapp.pojo.Rates
import com.happy.ricedetailsapp.pojo.SeaPortContent
import kotlinx.coroutines.*

class DashboardViewModel: ViewModel() {
    var checkedPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var seaPortPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var packagingPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var selectedCurrencyKey:MutableLiveData<String> = MutableLiveData<String>()
    var selectedCurrencySymbol:MutableLiveData<String> = MutableLiveData<String>()
    var seaPortItem:MutableLiveData<SeaPortContent> = MutableLiveData<SeaPortContent>()
    var currencyRates:MutableLiveData<Map<String?, Double>> = MutableLiveData<Map<String?, Double>>()
    var dollarRupeeFactor:MutableLiveData<Double> = MutableLiveData<Double>()
    init {
        packagingPosition.value = 0
        checkedPosition.value = 0
        seaPortPosition.value = 0
        selectedCurrencySymbol.value = "$"
        selectedCurrencyKey.value = "USD"
    }
    fun readDashboardFile(context:Context): LiveData<String> {
        var mDashboardFileLiveData: MutableLiveData<String> = MutableLiveData<String>()
        val url = "https://webhook.site/1bf0b1b8-d26a-4b68-b17f-f44cf1414768"
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

    fun getCurrencyApiData(context:Context): LiveData<String> {
        var mCurrencyApiLiveData: MutableLiveData<String> = MutableLiveData<String>()
        val url = "https://api.ratesapi.io/api/latest?base=INR"
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val job = async { FileDataCoroutines().getDataFromServer(url, context) }
                val mCoroutineResponse = job.await()
                withContext(Dispatchers.Main){
                    if(mCoroutineResponse.status == 0){
                        if(mCoroutineResponse.dataString!=null&&mCoroutineResponse.dataString!!.length>0)
                            mCurrencyApiLiveData.value = mCoroutineResponse.dataString
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mCurrencyApiLiveData
    }
}