package com.happy.ricedetailsapp.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [DashboardEntity::class],
    version = 35,
    exportSchema = false
)
@TypeConverters(CustomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dashboardDao(): DashboardDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "RiceDb.db"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Log.d("RiceDb.db", "Database created")
                            super.onCreate(db)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            Log.d("RiceDb.db", "Database opened")
                            super.onOpen(db)
                        }
                    })
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}