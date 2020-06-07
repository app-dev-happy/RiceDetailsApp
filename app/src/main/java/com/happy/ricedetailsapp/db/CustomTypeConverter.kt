package com.happy.ricedetailsapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo

class CustomTypeConverter {
    @TypeConverter
    fun fromDashBoardToString(it: DashBoardMainPojo): String {
        return it.let {
            Gson().toJson(it, DashBoardMainPojo::class.java)
        }
    }

    @TypeConverter
    fun fromStringToDashBoard(it: String): DashBoardMainPojo {
        return it.let {
            Gson().fromJson(it, DashBoardMainPojo::class.java)
        }
    }
}