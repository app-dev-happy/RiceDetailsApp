package com.happy.ricedetailsapp

import android.app.Application
import com.happy.ricedetailsapp.db.AppDataBase

class AppApplication:Application() {
    var mAppDatabase: AppDataBase? = null
    fun getAppDataBase():AppDataBase{
        mAppDatabase = AppDataBase.Companion.getDatabaseInstance(this)
        return mAppDatabase!!
    }
}
