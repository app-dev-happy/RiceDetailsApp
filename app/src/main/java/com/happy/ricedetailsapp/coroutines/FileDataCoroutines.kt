package com.happy.ricedetailsapp.FileDataCoroutines

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.happy.ricedetailsapp.coroutines.CoroutinesResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.security.Key
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import kotlin.collections.HashMap

class FileDataCoroutines {

    suspend fun getDataFromServer(url: String,context: Context): CoroutinesResponse {

        var fileDetailJob = GlobalScope.async {
            getUrlDataAsync(url,context) as CoroutinesResponse
        }
        return fileDetailJob.await();

    }
    fun getUrlDataAsync(url: String,context: Context): CoroutinesResponse {
        var lastTime = System.currentTimeMillis()
        val mCoroutinesResponse = CoroutinesResponse()
        val responseEntity = HashMap<String, Any>()
        var responseString = "error"
        try {

            val client = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()


            val requestBuilder = okhttp3.Request.Builder()
                    .url(url)
                    .get()

            val request = requestBuilder.build()
            Log.d("Request", request.toString())
            val response = client.newCall(request).execute()
            Log.d("Request", "Time: " + (System.currentTimeMillis() - lastTime))
            val responseCode = response.code()
            if (responseCode == 200) {
                responseString = response.body()!!.string()
                Log.d("responseString", responseString)
                responseEntity.put("Response", responseString)
                mCoroutinesResponse.dataString = responseString
                mCoroutinesResponse.status = 0

            } else {
                responseString = response.body()!!.string()
                Log.d("responseString", responseString)
                responseEntity.put("Response", responseString)
                mCoroutinesResponse.responseEntity = responseEntity
                mCoroutinesResponse.status = 1
            }


        } catch (e: Exception) {

            Log.d("responseString", responseString)
            responseEntity.put("Response", responseString)
            mCoroutinesResponse.responseEntity = responseEntity
            mCoroutinesResponse.status = 1
            e.printStackTrace()
        }

        return mCoroutinesResponse
    }
}