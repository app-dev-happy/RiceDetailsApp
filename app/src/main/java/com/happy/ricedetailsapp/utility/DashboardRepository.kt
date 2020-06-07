package com.happy.ricedetailsapp.utility

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.happy.ricedetailsapp.coroutines.CoroutinesResponse
import com.happy.ricedetailsapp.db.AppDatabase
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

    fun setFilesInDb(context: Context,dashBoardMainPojo: DashBoardMainPojo){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.getInstance(context).dashboardDao().insertDashboardData(DashboardEntity(AppContant.DashboardFileName,dashBoardMainPojo))
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }
    fun getDbFile(context: Context): LiveData<DashBoardMainPojo> {
        return AppDatabase.getInstance(context).dashboardDao().getDashboardData(AppContant.DashboardFileName)
    }

}