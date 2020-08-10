package com.happy.ricedetailsapp.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.happy.ricedetailsapp.db.AppDatabase
import com.happy.ricedetailsapp.db.CurrencyEntity
import com.happy.ricedetailsapp.db.DashboardEntity
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import kotlinx.coroutines.*

object DashboardRepository {
    private val PREFS_NAME = "auth_info"

    fun addString(con: Context?, key: String, value: String?) {
        try {
            if (con != null) {
                val sp = con.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val ed = sp.edit()
                ed.putString(key, value)
                ed.commit()
            }
        } catch (e: Exception) {
        }

    }
    fun getString(con: Context?, key: String, defaultValue: String): String {
        var result: String? = defaultValue
        if (con != null) {
            val sp = con.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            result = sp.getString(key, defaultValue)
        }
        return result!!
    }

    fun setFilesInDb(context: Context,dashBoardMainPojo: String?){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.getInstance(context).dashboardDao().insertDashboardData(DashboardEntity(AppConstant.DashboardFileName,dashBoardMainPojo!!))
            } catch (e: Exception) {
                Log.d("setdbData", e.toString())
               e.printStackTrace()
            }
        }
    }
    fun setCurrencyDataInDb(context: Context,currencyMap: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.getInstance(context).currencyDao().insertCurrencyData(CurrencyEntity(AppConstant.CurrencyAPIname,currencyMap))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getCurrencyDbData(context: Context): LiveData<String> {
        val mDb = AppDatabase.getInstance(context)
        val list = MutableLiveData<String>()
        try {

            if (mDb.isOpen) {
                return mDb.currencyDao().getCurrencyData(AppConstant.CurrencyAPIname)
            }
        } catch (e: Exception) {
           e.printStackTrace()
        }
        return list
    }

    fun getDbFile(context: Context): LiveData<String> {
        val mDb = AppDatabase.getInstance(context)
        val list = MutableLiveData<String>()
        try {

            if (mDb.isOpen) {
                return mDb.dashboardDao().getDashboardData(AppConstant.DashboardFileName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

}