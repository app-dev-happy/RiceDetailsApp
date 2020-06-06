package com.happy.ricedetailsapp.utility

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.happy.ricedetailsapp.coroutines.CoroutinesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

}