package com.happy.ricedetailsapp

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        instance = this
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}