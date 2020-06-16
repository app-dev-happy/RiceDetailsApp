package com.happy.ricedetailsapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.happy.ricedetailsapp.utility.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object NetworkClient {
    var client = OkHttpClient()

    fun getDashboardData() : LiveData<String>
    {
        var resp = MutableLiveData<String>()

        var request: Request = Request.Builder()
            .url(AppConstant.URL)
            .get()
            .build()

        Log.d("Request",AppConstant.URL)

        GlobalScope.launch(Dispatchers.IO) {
            try {
            var response: Response = client.newCall(request).execute()
            var result = response.body()?.string()

            if(response.isSuccessful) {
                resp.postValue(
                   result
                )
            }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return resp
    }

}