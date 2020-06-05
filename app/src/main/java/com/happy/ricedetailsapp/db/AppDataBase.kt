package com.happy.ricedetailsapp.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AppDataBase : RoomDatabase(){
    companion object {
        private var INSTANCE: AppDataBase? = null
        // Database version
        fun getDatabaseInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDataBase::class.java, "DB.db")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}