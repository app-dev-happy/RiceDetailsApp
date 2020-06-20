package com.happy.ricedetailsapp.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.happy.ricedetailsapp.FileDataCoroutines.FileDataCoroutines
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.utility.DashboardRepository
import kotlinx.coroutines.*


class DashboardViewModel: ViewModel() {
    var checkedPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var seaPortPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var packagingPosition:MutableLiveData<Int> = MutableLiveData<Int>()
    var selectedCurrencyKey:MutableLiveData<String> = MutableLiveData<String>()
    var selectedCurrencySymbol:MutableLiveData<String> = MutableLiveData<String>()
    var currencyRates:MutableLiveData<Map<String?, Double>> = MutableLiveData<Map<String?, Double>>()
    var dollarRupeeFactor:MutableLiveData<Double> = MutableLiveData<Double>()
    init {
        packagingPosition.value = 0
        checkedPosition.value = 0
        seaPortPosition.value = 0
        selectedCurrencySymbol.value = "$"
        selectedCurrencyKey.value = "USD"
    }
    fun readDashboardFile(context: Context
    ) {
        /*NetworkClient.getDashboardData().observe(context as LifecycleOwner, Observer {
            var dashBoardMainPojo:DashBoardMainPojo?=null
            try {
                dashBoardMainPojo = Gson().fromJson(
                    it,
                    DashBoardMainPojo::class.java
                )
                DashboardRepository.setFilesInDb(context, dashBoardMainPojo)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        })*/

        try{
            val storage = FirebaseStorage.getInstance()
            val httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/ricedetailsapp.appspot.com/o/AndroidDashboardFile.txt")
                httpsReference.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri>{
                override fun onSuccess(p0: Uri?) {
                    AppConstant.URL = p0.toString()
                    readData(context)
                }
            }).addOnFailureListener {
                    Log.d("error", it.stackTrace.toString())
                }
    } catch (ex: Exception) {
            ex.printStackTrace()
    }
    }
    fun readData(context: Context){
        try{
        CoroutineScope(Dispatchers.IO).launch {
            val job = async { FileDataCoroutines().getDataFromServer(AppConstant.URL, context) }
            val mCoroutineResponse = job.await()
            withContext(Dispatchers.Main){
                if(mCoroutineResponse.status == 0){
                    if(mCoroutineResponse.dataString!=null&&mCoroutineResponse.dataString!!.length>0) {
                        var dashBoardMainPojo:DashBoardMainPojo?=null
                        try {
                            dashBoardMainPojo = Gson().fromJson(
                                mCoroutineResponse.dataString,
                                DashBoardMainPojo::class.java
                            )
                            DashboardRepository.setFilesInDb(context, dashBoardMainPojo)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }

                    }
                }
            }
        }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun getDbDashboardFile(context:Context):LiveData<DashBoardMainPojo>{
        return DashboardRepository.getDbFile(context)
    }


    fun getCurrencyApiData(context:Context): LiveData<String> {
        var mCurrencyApiLiveData: MutableLiveData<String> = MutableLiveData<String>()
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val job = async { FileDataCoroutines().getDataFromServer(AppConstant.RATE_API_URL, context) }
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