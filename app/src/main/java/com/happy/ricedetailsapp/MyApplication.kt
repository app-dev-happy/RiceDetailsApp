package com.happy.ricedetailsapp

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        instance = this
    }

    @Synchronized
    fun getInstance(): MyApplication? {
        if (instance == null) {
            instance = MyApplication()
        }
        return instance!!
    }
    companion object {
        var instance: MyApplication? = null
    }
}