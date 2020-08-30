package com.happy.ricedetailsapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happy.ricedetailsapp.FileDataCoroutines.FileDataCoroutines
import com.happy.ricedetailsapp.pojo.KgsWeightItem
import com.happy.ricedetailsapp.utility.AppConstant
import com.happy.ricedetailsapp.utility.DashboardRepository
import com.happy.ricedetailsapp.utility.DashboardRepository.getString
import kotlinx.coroutines.*


class DashboardViewModel : ViewModel() {
    var checkedPosition: MutableLiveData<Int> = MutableLiveData<Int>()
    var seaPortPosition: MutableLiveData<Int> = MutableLiveData<Int>()
    var packagingPosition: MutableLiveData<Int> = MutableLiveData<Int>()
    var selectedCurrencyKey: MutableLiveData<String> = MutableLiveData<String>()
    var dataString: MutableLiveData<String> = MutableLiveData<String>()
    var selectedCurrencySymbol: MutableLiveData<String> = MutableLiveData<String>()
    var currencyRates: MutableLiveData<Map<String?, Double>> =
        MutableLiveData<Map<String?, Double>>()
    var rateCardPosition: MutableLiveData<Int> = MutableLiveData<Int>()
    var isPermissionGranted:MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var rateCardValue: MutableLiveData<KgsWeightItem> = MutableLiveData<KgsWeightItem>()

    init {
        packagingPosition.value = 0
        checkedPosition.value = 0
        seaPortPosition.value = 0
        rateCardPosition.value = 0
        isPermissionGranted.value = false
        selectedCurrencySymbol.value = "$"
        selectedCurrencyKey.value = "USD"
    }

    fun readDashboardFile(context: Context) {
/*        NetworkClient.getDashboardData().observe(context as LifecycleOwner, Observer {
            try {
                val dashBoardMainPojo = Gson().fromJson(
                    it,
                    DashBoardMainPojo::class.java
                )

                DashboardRepository.setFilesInDb(context, dashBoardMainPojo)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        })*/
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val job = async { FileDataCoroutines().getDataFromServer(AppConstant.URL, context) }
                val mCoroutineResponse = job.await()
                withContext(Dispatchers.Main) {
                    if (mCoroutineResponse.status == 0) {
                        if (mCoroutineResponse.dataString != null && !mCoroutineResponse.dataString.isNullOrEmpty()&&mCoroutineResponse.dataString!!.length > 0) {
                            try {
                                DashboardRepository.setFilesInDb(context, mCoroutineResponse.dataString)
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

    fun getDbDashboardFile(context: Context): LiveData<String> {
        return DashboardRepository.getDbFile(context)
    }

    fun getDbCurrencyFile(context: Context): LiveData<String> {
        return DashboardRepository.getCurrencyDbData(context)
    }


    fun readCurrencyApiData(context: Context) {
        var mCurrencyApiLiveData: MutableLiveData<String> = MutableLiveData<String>()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val job = async {
                    FileDataCoroutines().getDataFromServer(
                        AppConstant.RATE_API_URL,
                        context
                    )
                }
                val mCoroutineResponse = job.await()
                withContext(Dispatchers.Main) {
                    if (mCoroutineResponse.status == 0) {
                        if (mCoroutineResponse.dataString != null && mCoroutineResponse.dataString!!.length > 0)
                            mCurrencyApiLiveData.value = mCoroutineResponse.dataString
                        if (mCoroutineResponse.dataString != null && mCoroutineResponse.dataString!!.length>0) {

                            try {
                                DashboardRepository.setCurrencyDataInDb(
                                    context,
                                    mCoroutineResponse.dataString!!
                                )
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
}